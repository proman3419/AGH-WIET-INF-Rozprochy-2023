import re
from typing import Optional, Tuple, Any

from starlette import status
from starlette.responses import JSONResponse

from constants import DELIMITER, ERROR_MESSAGE
from helpers import str_to_percs, float_to_perc, get_sub_exercises_names_times
from remote_apis import get_all_data


def validate_plan_meal_input(exercise: str, meal: str, meal_percs: str) -> Optional[JSONResponse]:
    meal = meal.strip() # Filter spaces only string
    error_source = None
    if not re.compile(f"^((0|[1-9][0-9]*)min [A-Za-z ]+{DELIMITER} *)*((0|[1-9][0-9]*)min [A-Za-z ]+)$").match(exercise):
        error_source = "exercise"
    elif not re.compile("^([A-Za-z ]+, *)*[A-Za-z ]+$").match(meal):
        error_source = "meal"
    elif not re.compile("^((0|[1-9][0-9]*), *)*(0|[1-9][0-9]*)$").match(meal_percs):
        error_source = "meal_percs"
    if error_source is not None:
        return JSONResponse(status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
                     content={ERROR_MESSAGE: f"Invalid {error_source} format"})
    meal_percs = meal_percs.split(DELIMITER)
    if not len(meal.split(DELIMITER)) == len(meal_percs):
        return JSONResponse(status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
                            content={ERROR_MESSAGE: "There should be the same amout of meal and meal_percs " +
                                     f"values separated by {DELIMITER}"})
    meal_percs_sum = sum(map(float, meal_percs))
    if not meal_percs_sum == 100:
        return JSONResponse(status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
                            content={ERROR_MESSAGE: f"meal_percs should sum up to 100%, got {meal_percs_sum}%"})
    return None


# Calculate how much one needs to eat to regain to_regain_perc% of the burned energy
def get_plan_meal_raw(exercise: str, meal: str, meal_percs: str, to_regain_perc: float) -> Tuple[Any, Any]:
    error_response = validate_plan_meal_input(exercise, meal, meal_percs)
    if error_response is not None:
        return error_response, None
    meal_percs = str_to_percs(meal_percs)
    to_regain_perc = float_to_perc(to_regain_perc)
    sub_meals_names = list(map(lambda x: x.strip(), meal.split(DELIMITER)))
    sub_exercises_names, sub_exercises_times = get_sub_exercises_names_times(exercise)

    error_response, sub_exercises, sub_meals = get_all_data(DELIMITER.join(sub_exercises_names), meal, sub_meals_names)
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
