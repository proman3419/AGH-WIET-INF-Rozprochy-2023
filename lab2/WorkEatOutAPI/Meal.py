from typing import Dict, Any


class Meal:
    def __init__(self, name: str, energy: float, weight: float):
        self.name = name
        self.energy = energy
        self.weight = weight

    def to_html(self):
        return f"<li>{self.name}<ul><li>energy: {self.energy:.2f} kcal</li><li>weight: {self.weight:.2f} g</li></ul></li>"


def sub_meals_to_html(plan: Dict[str, Any]) -> str:
    return "".join([x.to_html() for x in plan["sub_meals"]])
