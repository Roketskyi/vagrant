# Student Management System

Spring Boot REST API застосунок для управління студентами та користувачами.

## Технології

- Spring Boot 2.7.0
- Spring Web
- Spring Boot Validation
- Lombok
- Springdoc OpenAPI (Swagger)
- MySQL

## Вимоги

- Java 11 або вище
- Maven
- MySQL 8.0

## Встановлення

1. Клонуйте репозиторій:
```bash
git clone <repository-url>
cd student-management
```

2. Налаштуйте базу даних:
```sql
CREATE DATABASE students_db;
CREATE USER 'students_user'@'localhost' IDENTIFIED BY 'students_password';
GRANT ALL PRIVILEGES ON students_db.* TO 'students_user'@'localhost';
FLUSH PRIVILEGES;
```

3. Зберіть проект:
```bash
mvn clean install
```

4. Запустіть додаток:
```bash
mvn spring-boot:run
```

## API Endpoints

- GET /api/students - Отримати всіх студентів
- GET /api/students/{id} - Отримати студента за ID
- POST /api/students - Створити нового студента
- PUT /api/students/{id} - Оновити студента
- DELETE /api/students/{id} - Видалити студента

## Swagger Documentation

Swagger UI доступний за адресою: http://localhost:8080/swagger-ui.html

## Валідація

- Ім'я студента: не null, розмір від 2 до 50 символів
- Вік студента: від 16 до 100 років

## Структура проекту

```
src/main/java/
├── com.students
│   ├── controller
│   │   ├── HomeController.java
│   │   └── StudentRestController.java
│   ├── dto
│   │   ├── RequestStudentDTO.java
│   │   └── ResponseStudentDTO.java
│   ├── model
│   │   ├── Student.java
│   │   ├── User.java
│   │   └── Role.java
│   ├── repository
│   │   └── StudentRepository.java
│   └── service
│       ├── StudentService.java
│       └── impl
│           └── StudentServiceImpl.java
``` 