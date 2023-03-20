# WorkEatOutAPI
API pozwalające na zaplanowanie posiłku lub treningu pod kątem bilansu energetycznego.

## Endpointy
### /plan_exercise_raw
#### GET
Zwraca JSON z podsumowaniem posiłku oraz plan treningu.
- api_key - klucz do API
- exercise - lista aktywności, którymi jesteśmy zainteresowani (np. "swimming, running")
- exercise_percs - jaki procent energii powinien być spalony przez daną aktywność (np. "70, 30")
- meal - lista potraw wraz z ich wagą (np. "250g lasagne, 50g broccoli, 250g tomato soup")
- to_burn_perc - procent energii posiłku, który powinien zostać spalony (np. "70")

### /plan_meal_raw
#### GET
Zwraca JSON z podsumowaniem treningu oraz plan posiłku.
- api_key - klucz do API
- exercise - lista aktywności wraz z czasami wykonania (np. "100min swimming, 30min running")
- meal - lista potraw, którymi jesteśmy zainteresowani (np. "lasagne, broccoli, tomato soup")
- exercise_percs - jaki procent energii powinien być odzyskany przez daną potrawę (np. "60, 10, 30")
- to_regain_perc - procent energii treningu, który powinien zostać odzyskany (np. "70")

### /plan_exercise
#### GET
Zwraca HTML z podsumowaniem posiłku oraz plan treningu.
Analogicznie jak `/plan_exercise_raw`

### /plan_meal
#### GET
Zwraca JSON z podsumowaniem treningu oraz plan posiłku.
Analogicznie jak `/plan_meal_raw`

### /input
#### GET
Zwraca formularz, w którym można wypełnić parametry dla `/plan_exercise`, `/plan_meal` i wykonać zapytanie.

## Użyte zewnętrzne API
- [https://www.edamam.com/](Edamam) - kaloryczność potraw
- [https://www.nutritionix.com/](Nutritionix) - spalanie energii podczas aktywności
