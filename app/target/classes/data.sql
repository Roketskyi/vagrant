-- Додавання тестових студентів
INSERT INTO students (name, age, grade) VALUES
('Ivan Petrenko', 20, 85.5),
('Maria Kovalenko', 19, 92.0),
('Oleksandr Shev4enko', 21, 78.5)
ON DUPLICATE KEY UPDATE name=name; 