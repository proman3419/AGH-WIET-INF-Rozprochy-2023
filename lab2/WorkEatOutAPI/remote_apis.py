from typing import Tuple, Any, List, Optional

import asyncio
import aiohttp
from starlette import status
from starlette.responses import JSONResponse

from Exercise import Exercise
from Meal import Meal
from constants import MOCK_RESPONSES, NUTRITIONIX_EXERCISE_URL, NUTRITIONIX_AUTH_HEADERS, \
    EDAMAM_FOOD_PARSER_URL, EDAMAM_FOOD_PARAMS, EDAMAM_DELIMITER, DELIMITER, EDAMAM_GRAMS_PER_SERVING, ERROR_MESSAGE, \
    NUTRITIONIX_DELIMITER
from helpers import load_response_body


async def get_response_bodies(exercise: str, meal: str) -> Any:
    input_coroutines = [get_exercise_details(exercise), get_meal_details(meal)]
    result = await asyncio.gather(*input_coroutines)
    return result


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

        # Wait for coroutines to complete
        exercise_details, meal_details = asyncio.get_event_loop().run_until_complete(get_response_bodies(exercise, meal))

        response_body = take_error_into_account(exercise_details)
        sub_exercises = get_sub_exercises(response_body)

        response_body = take_error_into_account(meal_details)
        sub_meals = take_error_into_account(get_sub_meals(response_body, sub_meals_names))
    except Exception as ignored:
        # Fail early
        pass
    return error_response, sub_exercises, sub_meals


async def get_exercise_details(exercise: str) -> Tuple[JSONResponse, Any]:
    error_response = None
    if MOCK_RESPONSES:
        response_body = load_response_body("responses/nutritionix/exercise.json")
    else:
        async with aiohttp.ClientSession() as session:
            async with session.post(url=NUTRITIONIX_EXERCISE_URL,
                                     data={"query": exercise},
                                     headers=NUTRITIONIX_AUTH_HEADERS) as response:
                response_body = await response.json()
                if response.status != status.HTTP_200_OK:
                    error_response = JSONResponse(status_code=response.status,
                                                  content=f"Nutritionix API: {response_body}")
    return error_response, response_body


def get_sub_exercises(response_body: Any) -> List[Exercise]:
    sub_exercises = []
    for sub_exercise in response_body["exercises"]:
        sub_exercises.append(Exercise(sub_exercise["user_input"],
                                      sub_exercise["nf_calories"] / sub_exercise["duration_min"],
                                      1))
    return sub_exercises


async def get_meal_details(meal: str) -> Tuple[JSONResponse, Any]:
    error_response = None
    if MOCK_RESPONSES:
        response_body = load_response_body("responses/edamam/food.json")
    else:
        async with aiohttp.ClientSession() as session:
            async with session.get(url=EDAMAM_FOOD_PARSER_URL,
                                   params=EDAMAM_FOOD_PARAMS | {
                                       "ingr": EDAMAM_DELIMITER.join([x.strip() for x in meal.split(DELIMITER)])}) as response:
                response_body = await response.json()
                if response.status != status.HTTP_200_OK:
                    error_response = JSONResponse(status_code=response.status,
                                                  content=f"Edamam API: {response_body}")
    return error_response, response_body


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
