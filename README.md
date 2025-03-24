# Система управління студентами

Це простий Spring Boot додаток для управління списком студентів з використанням Thymeleaf та Bootstrap.

## Вимоги

- Java 11 або вище
- Maven
- Vagrant
- VirtualBox

## Встановлення та запуск

1. Клонуйте репозиторій:
```bash
git clone <url-репозиторію>
cd student-management
```

2. Запустіть віртуальну машину за допомогою Vagrant:
```bash
vagrant up
```

3. Підключіться до віртуальної машини:
```bash
vagrant ssh
```

4. Перейдіть до директорії проекту:
```bash
cd /vagrant
```

5. Зберіть проект за допомогою Maven:
```bash
mvn clean package
```

6. Запустіть додаток:
```bash
java -jar target/student-management-0.0.1-SNAPSHOT.jar
```

7. Відкрийте браузер і перейдіть за адресою:
```
http://localhost:8080/students
```

## Функціональність

- Перегляд списку студентів
- Додавання нового студента
- Редагування існуючого студента
- Видалення студента
- Фільтрація студентів за віком
- Валідація даних форми

## Технології

- Spring Boot
- Spring MVC
- Thymeleaf
- Bootstrap
- Lombok
- Maven 