-- Додавання ролей
MERGE INTO roles (name) KEY(name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

-- Додавання тестових студентів
MERGE INTO students (name, age, grade) KEY(name)
VALUES ('Ivan Petrenko', 20, 85.5),
       ('Maria Kovalenko', 19, 92.0),
       ('Oleksandr Shev4enko', 21, 78.5); 