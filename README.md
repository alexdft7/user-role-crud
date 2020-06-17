# user-role-crud
Пример JSON для /add: {"name": "Пётр", "login": "petrushka", "password": "PaRoL1", "roles": [{"role": "Оператор"}]} 
Пример JSON для /edit: {"id": 6, "name": "Пётр", "login": "petrushka", "password": "PaRoL1", "roles": [{"role": "Оператор"}]} 

Если ввести роль неверно (например, "Оператр" вместо "Оператор"), то она просто не добавится.
