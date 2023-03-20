from typing import Optional, Set

from fastapi import FastAPI, status
from starlette.responses import JSONResponse, HTMLResponse

from Exercise import sub_exercises_to_html
from Meal import sub_meals_to_html
from plan_exercise import get_plan_exercise_raw
from plan_meal import get_plan_meal_raw
from constants import *
from helpers import *


def load_authorized_keys() -> Set:
    with open(AUTHORIZED_KEYS_FILE_PATH, "r+") as f:
        return set(f.read().splitlines())


authorized_keys = load_authorized_keys()
app = FastAPI()


def authorize(key: str) -> Optional[JSONResponse]:
    if key in authorized_keys:
        return None
    return JSONResponse(status_code=status.HTTP_401_UNAUTHORIZED,
                        content={ERROR_MESSAGE: ERROR_UNAUTHORIZED})


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
