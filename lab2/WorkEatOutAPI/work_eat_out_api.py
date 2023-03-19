from constants import *
from fastapi import FastAPI, status
from starlette.responses import JSONResponse
import requests
import json
from typing import List, Dict, Tuple, Any


app = FastAPI()


def load_response_body(file_path: str) -> Dict:
    with open(file_path, "r", encoding='utf-8') as f:
        response = json.load(f)
    return response


def str_to_percs(s: str) -> List[float]:
    return list(map(lambda x: float_to_perc(float(x)), s.split(DELIMITER)))


def float_to_perc(f: float) -> float:
    return f / 100


def get_sub_meals_raw(meal: str) -> List[Tuple[str, float]]:
    sub_meals_raw = []
    for sub_meal in meal.split(DELIMITER):
        weight_raw, name_raw = sub_meal.split(maxsplit=1)
        weight = float(weight_raw[:-1]) # Remove the "g"
        name = name_raw.strip()
        sub_meals_raw.append((name, weight))
    return sub_meals_raw


def get_sub_exercises_raw(exercise: str) -> List[Tuple[str, float]]:
    sub_exercises_raw = []
    for sub_exercise in exercise.split(DELIMITER):
        time_raw, name_raw = sub_exercise.split(maxsplit=1)
        time = float(time_raw[:-3]) # Remove the "min"
        name = name_raw.strip()
        sub_exercises_raw.append((name, time))
    return sub_exercises_raw


def objects_to_list(objects: List[object]) -> list[dict[str, Any]]:
    return list(map(vars, objects))


class Exercise:
    def __init__(self, name: str, energy: float, time: float):
        self.name = name
        self.energy = energy
        self.time = time


class Meal:
    def __init__(self, name: str, energy: float, weight: float):
        self.name = name
        self.energy = energy
        self.weight = weight


# Calculate how much one needs to exercise to burn to_burn_perc% of the meal
@app.get("/plan_exercise")
async def plan_exercise(exercise: str, exercise_percs: str, meal: str, to_burn_perc: float):
    exercise = exercise.replace(DELIMITER, NUTRITIONIX_DELIMITER)
    exercise_percs = str_to_percs(exercise_percs)
    to_burn_perc = float_to_perc(to_burn_perc)

    # Get information about the exercise's kcal consumption
    if MOCK_RESPONSES:
        response_body = load_response_body("responses/nutritionix/exercise.json")
    else:
        response = requests.post(url=NUTRITIONIX_EXERCISE_URL,
                                 data={"query": exercise},
                                 headers=NUTRITIONIX_AUTH_HEADERS)
        response_body = response.json()
        if response.status_code != status.HTTP_200_OK:
            return JSONResponse(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                                content=response_body)

    # Sum kcal of all exercises taking exercise_percs into account
    sub_exercises = []
    to_burn_energy_per_default_time = 0
    for i, sub_exercise in enumerate(response_body["exercises"]):
        try:
            sub_exercises.append(Exercise(sub_exercise["user_input"],
                                          sub_exercise["nf_calories"] * exercise_percs[i],
                                          NUTRITIONIX_DEFAULT_EXERCISE_TIME_MIN * exercise_percs[i]))
            to_burn_energy_per_default_time += sub_exercises[-1].energy
        except IndexError:
            return JSONResponse(status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
                                content={ERROR_MESSAGE: ERROR_WRONG_ARGS_COUNT})

    # Get information about the meal's kcal
    if MOCK_RESPONSES:
        response_body = load_response_body("responses/edamam/food.json")
    else:
        response = requests.get(url=EDAMAM_FOOD_PARSER_URL,
                                 params=EDAMAM_FOOD_PARAMS | {"ingr": meal.replace(DELIMITER, EDAMAM_DELIMITER)})
        response_body = response.json()
        if response.status_code != status.HTTP_200_OK:
            return JSONResponse(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                                content=response_body)

    # Sum kcal of the meal
    sub_meals_raw = get_sub_meals_raw(meal)
    sub_meals = []
    meal_provided_energy = 0
    for i, sub_meal in enumerate(map(lambda x: x["food"], response_body["parsed"])):
        kcal_per_serving = sub_meal["nutrients"]["ENERC_KCAL"]
        try:
            sub_meals.append(Meal(sub_meals_raw[i][0],
                                  kcal_per_serving * sub_meals_raw[i][1] / EDAMAM_GRAMS_PER_SERVING,
                                  sub_meals_raw[i][1]))
        except IndexError:
            return JSONResponse(status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
                                content={ERROR_MESSAGE: ERROR_WRONG_ARGS_COUNT})
        meal_provided_energy += sub_meals[-1].energy

    to_burn_energy = meal_provided_energy * to_burn_perc
    to_burn_energy_per_min = to_burn_energy_per_default_time / NUTRITIONIX_DEFAULT_EXERCISE_TIME_MIN
    exercise_time = to_burn_energy / to_burn_energy_per_min

    for i, sub_exercise in enumerate(sub_exercises):
        sub_exercise.time = exercise_time * exercise_percs[i]
        sub_exercise.energy = to_burn_energy * exercise_percs[i]

    return {"meal_provided_energy": meal_provided_energy,
            "sub_meals": objects_to_list(sub_meals),
            "exercise_time": exercise_time,
            "to_burn_energy": to_burn_energy,
            "sub_exercises": objects_to_list(sub_exercises)}


# Calculate how much one needs to eat to regain to_regain_perc% of the burned energy
@app.get("/plan_meal")
async def plan_meal(exercise: str, meal: str, meal_percs: str, to_regain_perc: float):
    exercise = exercise.replace(DELIMITER, NUTRITIONIX_DELIMITER)
    meal_percs = str_to_percs(meal_percs)
    to_regain_perc = float_to_perc(to_regain_perc)

    # Get information about the exercise's kcal consumption
    if MOCK_RESPONSES:
        response_body = load_response_body("responses/nutritionix/exercise.json")
    else:
        response = requests.post(url=NUTRITIONIX_EXERCISE_URL,
                                 data={"query": exercise},
                                 headers=NUTRITIONIX_AUTH_HEADERS)
        response_body = response.json()
        if response.status_code != status.HTTP_200_OK:
            return JSONResponse(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                                content=response_body)

    # Sum kcal of all exercises
    sub_exercises_raw = get_sub_exercises_raw(exercise)
    sub_exercises = []
    exercise_burned_energy = 0
    for i, sub_exercise in enumerate(response_body["exercises"]):
        try:
            sub_exercises.append(Exercise(sub_exercise["user_input"],
                                          sub_exercise["nf_calories"] / NUTRITIONIX_DEFAULT_EXERCISE_TIME_MIN * sub_exercises_raw[i][1],
                                          sub_exercises_raw[i][1]))
            exercise_burned_energy += sub_exercises[-1].energy
        except IndexError:
            return JSONResponse(status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
                                content={ERROR_MESSAGE: ERROR_WRONG_ARGS_COUNT})

    # Get information about the meal's kcal
    if MOCK_RESPONSES:
        response_body = load_response_body("responses/edamam/food.json")
    else:
        response = requests.get(url=EDAMAM_FOOD_PARSER_URL,
                                 params=EDAMAM_FOOD_PARAMS | {"ingr": meal.replace(DELIMITER, EDAMAM_DELIMITER)})
        response_body = response.json()
        if response.status_code != status.HTTP_200_OK:
            return JSONResponse(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                                content=response_body)

    # Sum kcal of the meal
    sub_meals_names = list(map(lambda x: x.strip(), meal.split(DELIMITER)))
    sub_meals = []
    to_provide_energy = 0
    for i, sub_meal in enumerate(map(lambda x: x["food"], response_body["parsed"])):
        kcal_per_serving = sub_meal["nutrients"]["ENERC_KCAL"]
        try:
            sub_meals.append(Meal(sub_meals_names[i],
                                  kcal_per_serving * meal_percs[i],
                                  EDAMAM_GRAMS_PER_SERVING * meal_percs[i]))
        except IndexError:
            return JSONResponse(status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
                                content={ERROR_MESSAGE: ERROR_WRONG_ARGS_COUNT})
        to_provide_energy += sub_meals[-1].energy

    to_regain_energy = exercise_burned_energy * to_regain_perc
    servings = to_regain_energy / to_provide_energy

    for i, sub_meal in enumerate(sub_meals):
        sub_meal.weight *= servings
        sub_meal.energy *= servings

    return {"exercise_burned_energy": exercise_burned_energy,
            "sub_exercises": objects_to_list(sub_exercises),
            "to_regain_energy": to_regain_energy,
            "sub_meals": objects_to_list(sub_meals)}
