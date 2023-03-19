from constants import *
from fastapi import FastAPI, status
from starlette.responses import JSONResponse, HTMLResponse
import requests
import json
from typing import List, Dict, Tuple, Any, Optional, Set


def load_authorized_keys() -> Set:
    with open(AUTHORIZED_KEYS_FILE_PATH, "r+") as f:
        return set(f.read().splitlines())


authorized_keys = load_authorized_keys()
app = FastAPI()


def load_response_body(file_path: str) -> Dict:
    with open(file_path, "r", encoding='utf-8') as f:
        response = json.load(f)
    return response


def str_to_percs(s: str) -> List[float]:
    return list(map(lambda x: float_to_perc(float(x)), s.split(DELIMITER)))


def float_to_perc(f: float) -> float:
    return f / 100


def get_sub_meals_names_weights(meal: str) -> Tuple[List[str], List[float]]:
    sub_meals_names = []
    sub_meals_weights = []
    for sub_meal in meal.split(DELIMITER):
        weight_raw, name_raw = sub_meal.split(maxsplit=1)
        weight = float(weight_raw[:-1])  # Remove the "g"
        name = name_raw.strip()
        sub_meals_names.append(name)
        sub_meals_weights.append(weight)
    return sub_meals_names, sub_meals_weights


def get_sub_exercises_times(exercise: str) -> List[float]:
    sub_exercises_times = []
    for sub_exercise in exercise.split(DELIMITER):
        time_raw, name_raw = sub_exercise.split(maxsplit=1)
        time = float(time_raw[:-3])  # Remove the "min"
        sub_exercises_times.append(time)
    return sub_exercises_times


def objects_to_list(objects: List[object]) -> list[dict[str, Any]]:
    return list(map(vars, objects))


def get_exercise_details(exercise: str) -> Tuple[JSONResponse, Any]:
    print(exercise)
    error_response = None
    if MOCK_RESPONSES:
        response_body = load_response_body("responses/nutritionix/exercise.json")
    else:
        response = requests.post(url=NUTRITIONIX_EXERCISE_URL,
                                 data={"query": exercise},
                                 headers=NUTRITIONIX_AUTH_HEADERS)
        response_body = response.json()
        if response.status_code != status.HTTP_200_OK:
            error_response = JSONResponse(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                                          content=response_body)
    return error_response, response_body


def get_meal_details(meal: str) -> Tuple[JSONResponse, Any]:
    error_response = None
    if MOCK_RESPONSES:
        response_body = load_response_body("responses/edamam/food.json")
    else:
        response = requests.get(url=EDAMAM_FOOD_PARSER_URL,
                                params=EDAMAM_FOOD_PARAMS | {
                                    "ingr": EDAMAM_DELIMITER.join([x.strip() for x in meal.split(DELIMITER)])})
        response_body = response.json()
        if response.status_code != status.HTTP_200_OK:
            error_response = JSONResponse(status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                                          content=response_body)
    return error_response, response_body


class Exercise:
    def __init__(self, name: str, energy: float, time: float):
        self.name = name
        self.energy = energy
        self.time = time

    def to_html(self):
        return f"<li>{self.name}<ul><li>energy: {self.energy:.2f} kcal</li><li>time: {self.time:.2f} min</li></ul></li>"


class Meal:
    def __init__(self, name: str, energy: float, weight: float):
        self.name = name
        self.energy = energy
        self.weight = weight

    def to_html(self):
        return f"<li>{self.name}<ul><li>energy: {self.energy:.2f} kcal</li><li>weight: {self.weight:.2f} g</li></ul></li>"


def sub_meals_to_html(plan: Dict[str, Any]) -> str:
    return "".join([x.to_html() for x in plan["sub_meals"]])


def sub_exercises_to_html(plan: Dict[str, Any]) -> str:
    return "".join([x.to_html() for x in plan["sub_exercises"]])


def get_sub_exercises(response_body: Any) -> List[Exercise]:
    sub_exercises = []
    for sub_exercise in response_body["exercises"]:
        sub_exercises.append(Exercise(sub_exercise["user_input"],
                                      sub_exercise["nf_calories"] / sub_exercise["duration_min"],
                                      1))
    return sub_exercises


def get_sub_meals(response_body: Any, sub_meals_names: List[str]) -> Tuple[JSONResponse, List[Meal]]:
    error_response = None
    sub_meals = []
    for i, sub_meal in enumerate(map(lambda x: x["food"], response_body["parsed"])):
        kcal_per_serving = sub_meal["nutrients"]["ENERC_KCAL"]
        try:
            sub_meals.append(Meal(sub_meals_names[i],
                                  kcal_per_serving / EDAMAM_GRAMS_PER_SERVING,
                                  1))
        except IndexError:
            error_response = JSONResponse(status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
                                          content={ERROR_MESSAGE: f"Index: {i}, length: {len(sub_meals_names)}, "
                                                                  f"collection: {sub_meals_names}"})
    return error_response, sub_meals


def authorize(key: str) -> Optional[JSONResponse]:
    if key in authorized_keys:
        return None
    return JSONResponse(status_code=status.HTTP_401_UNAUTHORIZED,
                        content={ERROR_MESSAGE: ERROR_UNAUTHORIZED})


# Return:
#   sub_exercises - kcal / min
#   sub_meals - kcal / g
def get_all_data(exercise: str, meal: str, sub_meals_names: List[str]) -> [JSONResponse, List[Exercise], List[Meal]]:
    error_response = sub_exercises = sub_meals = None

    def take_error_into_account(args: Tuple[JSONResponse, Any]) -> Optional[Any]:
        nonlocal error_response
        _error_response = args[0]
        if _error_response is None:
            return args[1]
        else:
            error_response = _error_response
            raise Exception("Error response not None")

    try:
        exercise = exercise.replace(DELIMITER, NUTRITIONIX_DELIMITER)

        response_body = take_error_into_account(get_exercise_details(exercise))
        sub_exercises = get_sub_exercises(response_body)

        response_body = take_error_into_account(get_meal_details(meal))
        sub_meals = take_error_into_account(get_sub_meals(response_body, sub_meals_names))
    except Exception as ignored:
        pass
    return error_response, sub_exercises, sub_meals


# Calculate how much one needs to exercise to burn to_burn_perc% of the meal
def get_plan_exercise_raw(exercise: str, exercise_percs: str, meal: str, to_burn_perc: float) -> Tuple[Any, Any]:
    exercise = NUTRITIONIX_EXERCISE_PREFIX + exercise.replace(DELIMITER, NUTRITIONIX_EXERCISE_PREFIX)
    exercise_percs = str_to_percs(exercise_percs)
    to_burn_perc = float_to_perc(to_burn_perc)
    sub_meals_names, sub_meals_weights = get_sub_meals_names_weights(meal)

    error_response, sub_exercises, sub_meals = get_all_data(exercise, DELIMITER.join(sub_meals_names), sub_meals_names)
    if error_response is not None:
        return error_response, None

    # Take sub_meals' weights into account
    for i, sub_meal in enumerate(sub_meals):
        sub_meal.weight = sub_meals_weights[i]
        sub_meal.energy *= sub_meals_weights[i]

    # Calculate total meal's energy
    total_meal_energy = sum(map(lambda x: x.energy, sub_meals))

    # Calculate total energy to be burned
    to_burn_energy = to_burn_perc * total_meal_energy

    # Calculate energy to be burned and time for each sub_exercise
    for i, sub_exercise in enumerate(sub_exercises):
        sub_exercise_to_burn_energy = to_burn_energy * exercise_percs[i]
        sub_exercise.time = sub_exercise_to_burn_energy / sub_exercise.energy
        sub_exercise.energy = sub_exercise_to_burn_energy

    # Calculate exercise_time
    exercise_time = sum(map(lambda x: x.time, sub_exercises))

    return error_response, \
           {"meal_provided_energy": total_meal_energy,
            "sub_meals": sub_meals,
            "exercise_time": exercise_time,
            "to_burn_energy": to_burn_energy,
            "sub_exercises": sub_exercises}


# Return a json response
@app.get("/plan_exercise_raw")
async def plan_exercise_raw(api_key: str, exercise: str, exercise_percs: str, meal: str, to_burn_perc: float):
    error_response = authorize(api_key)
    if error_response is not None:
        return error_response
    error_response, exercise_plan = get_plan_exercise_raw(exercise, exercise_percs, meal, to_burn_perc)
    if error_response is not None:
        return error_response
    exercise_plan["sub_meals"] = objects_to_list(exercise_plan["sub_meals"])
    exercise_plan["sub_exercises"] = objects_to_list(exercise_plan["sub_exercises"])
    return exercise_plan


# Return a form with results
@app.get("/plan_exercise")
async def plan_exercise(api_key: str, exercise: str, exercise_percs: str, meal: str, to_burn_perc: float):
    error_response = authorize(api_key)
    if error_response is not None:
        return error_response
    error_response, exercise_plan = get_plan_exercise_raw(exercise, exercise_percs, meal, to_burn_perc)
    if error_response is not None:
        return error_response
    sub_meals_html = sub_meals_to_html(exercise_plan)
    sub_exercises_html = sub_exercises_to_html(exercise_plan)
    html_content = f"<h2>INPUT SUMMARY</h2><ol><li>Energy provided by the meal: {exercise_plan['meal_provided_energy']:.2f} kcal</li>" + \
                   f"<li>Meal:<ol>{sub_meals_html}</ol></li></ol>" + \
                   f"<h2>OUTPUT</h2><ol><li>Exercise time: {exercise_plan['exercise_time']:.2f} min</li>" + \
                   f"<li>Energy to burn: {exercise_plan['to_burn_energy']:.2f} kcal</li>" + \
                   f"<li>Exercise:<ol>{sub_exercises_html}</ol></li></ol>"
    return HTMLResponse(content=html_content, status_code=status.HTTP_200_OK)


# Calculate how much one needs to eat to regain to_regain_perc% of the burned energy
def get_plan_meal_raw(exercise: str, meal: str, meal_percs: str, to_regain_perc: float) -> Tuple[Any, Any]:
    meal_percs = str_to_percs(meal_percs)
    to_regain_perc = float_to_perc(to_regain_perc)
    sub_meals_names = list(map(lambda x: x.strip(), meal.split(DELIMITER)))
    sub_exercises_times = get_sub_exercises_times(exercise)

    error_response, sub_exercises, sub_meals = get_all_data(exercise, meal, sub_meals_names)
    if error_response is not None:
        return error_response, None

    # Take sub_exercises' times into account
    for i, sub_exercise in enumerate(sub_exercises):
        sub_exercise.time = sub_exercises_times[i]
        sub_exercise.energy *= sub_exercises_times[i]

    # Calculate total exercise's energy
    total_exercise_energy = sum(map(lambda x: x.energy, sub_exercises))

    # Calculate total energy to be regained
    to_regain_energy = to_regain_perc * total_exercise_energy

    # Calculate energy to be regained and weight for each sub_meal
    for i, sub_meal in enumerate(sub_meals):
        sub_meal_to_regain_energy = to_regain_energy * meal_percs[i]
        sub_meal.weight = sub_meal_to_regain_energy / sub_meal.energy
        sub_meal.energy = sub_meal_to_regain_energy

    return error_response, \
           {"exercise_burned_energy": total_exercise_energy,
            "sub_exercises": sub_exercises,
            "to_regain_energy": to_regain_energy,
            "sub_meals": sub_meals}


# Return a json response
@app.get("/plan_meal_raw")
async def plan_meal_raw(api_key: str, exercise: str, meal: str, meal_percs: str, to_regain_perc: float):
    error_response = authorize(api_key)
    if error_response is not None:
        return error_response
    error_response, exercise_plan = get_plan_meal_raw(exercise, meal, meal_percs, to_regain_perc)
    if error_response is not None:
        return error_response
    exercise_plan["sub_meals"] = objects_to_list(exercise_plan["sub_meals"])
    exercise_plan["sub_exercises"] = objects_to_list(exercise_plan["sub_exercises"])
    return exercise_plan


# Return a form with results
@app.get("/plan_meal")
async def plan_meal(api_key: str, exercise: str, meal: str, meal_percs: str, to_regain_perc: float):
    error_response = authorize(api_key)
    if error_response is not None:
        return error_response
    error_response, exercise_plan = get_plan_meal_raw(exercise, meal, meal_percs, to_regain_perc)
    if error_response is not None:
        return error_response
    sub_meals_html = sub_meals_to_html(exercise_plan)
    sub_exercises_html = sub_exercises_to_html(exercise_plan)
    html_content = f"<h2>INPUT SUMMARY</h2><ol><li>Energy burned during exercise: {exercise_plan['exercise_burned_energy']:.2f} kcal</li>" + \
                   f"<li>Exercise:<ol>{sub_exercises_html}</ol></li></ol>" + \
                   f"<h2>OUTPUT</h2><ol><li>Energy to regain: {exercise_plan['to_regain_energy']:.2f} kcal</li>" + \
                   f"<li>Meal:<ol>{sub_meals_html}</ol></li>"
    return HTMLResponse(content=html_content, status_code=status.HTTP_200_OK)


@app.get("/input")
async def plan_exercise():
    with open("input.html", "r") as f:
        html_content = f.read()
    return HTMLResponse(content=html_content, status_code=status.HTTP_200_OK)

# copy paste oriented programming ^-^
