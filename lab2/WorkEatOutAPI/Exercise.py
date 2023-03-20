from typing import Dict, Any


class Exercise:
    def __init__(self, name: str, energy: float, time: float):
        self.name = name
        self.energy = energy
        self.time = time

    def to_html(self):
        return f"<li>{self.name}<ul><li>energy: {self.energy:.2f} kcal</li><li>time: {self.time:.2f} min</li></ul></li>"


def sub_exercises_to_html(plan: Dict[str, Any]) -> str:
    return "".join([x.to_html() for x in plan["sub_exercises"]])



