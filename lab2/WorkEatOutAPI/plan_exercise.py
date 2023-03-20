import re
from typing import Optional, Tuple, Any

from starlette import status
from starlette.responses import JSONResponse

from constants import DELIMITER, ERROR_MESSAGE, NUTRITIONIX_EXERCISE_PREFIX
from helpers import str_to_percs, float_to_perc, get_sub_meals_names_weights
from remote_apis import get_all_data


def validate_plan_exercise_input(exercise: str, meal: str, exercise_percs: str) -> Optional[JSONResponse]:
    exercise = exercise.strip() # Filter spaces only string
    error_source = None
    if not re.compile(f"^([A-Za-z ]+{DELIMITER} *)*[A-Za-z ]+$").match(exercise):
        error_source = "exercise"
    elif not re.compile(f"^((0|[1-9][0-9]*)g [A-Za-z ]+{DELIMITER} *)*((0|[1-9][0-9]*)g [A-Za-z ]+)$").match(meal):
        error_source = "meal"
    elif not re.compile(f"^((0|[1-9][0-9]*){DELIMITER} *)*(0|[1-9][0-9]*)+$").match(exercise_percs):
        error_source = "exercise_percs"
    if error_source is not None:
        return JSONResponse(status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
                     content={ERROR_MESSAGE: f"Invalid {error_source} format"})
    exercise_percs = exercise_percs.split(DELIMITER)
    if not len(exercise.split(DELIMITER)) == len(exercise_percs):
        return JSONResponse(status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
                            content={ERROR_MESSAGE: "There should be the same amout of exercise and exercise_percs " +
                                     f"values separated by {DELIMITER}"})
    exercise_percs_sum = sum(map(float, exercise_percs))
    if not exercise_percs_sum == 100:
        return JSONResponse(status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
                            content={ERROR_MESSAGE: f"exercise_percs should sum up to 100%, got {exercise_percs_sum}%"})
    return None


# Calculate how much one needs to exercise to burn to_burn_perc% of the meal
def get_plan_exercise_raw(exercise: str, exercise_percs: str, meal: str, to_burn_perc: float) -> Tuple[Any, Any]:
    error_response = validate_plan_exercise_input(exercise, meal, exercise_percs)
    if error_response is not None:
        return error_response, None
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
