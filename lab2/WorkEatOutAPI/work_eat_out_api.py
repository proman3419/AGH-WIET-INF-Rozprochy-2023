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
        weight_raw, name = sub_meal.split(maxsplit=1)
        weight = float(weight_raw[:-1]) # Remove the "g"
        sub_meals_raw.append((name, weight))
    return sub_meals_raw


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
@app.get("/exercise_time")
async def exercise_time(exercise: str, exercise_percs: str, meal: str, to_burn_perc: float):
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
        response = requests.post(url=EDAMAM_FOOD_PARSER_URL,
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
