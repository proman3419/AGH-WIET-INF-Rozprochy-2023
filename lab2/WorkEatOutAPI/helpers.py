import json
from typing import Dict, List, Tuple, Any

from constants import DELIMITER


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
