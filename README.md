# Система управління студентами

Це Spring Boot застосунок для управління студентами та користувачами з REST API інтерфейсом.

## Технології

- Spring Boot 2.7.0
- Spring Web
- Spring Boot Validation
- Lombok
- Springdoc OpenAPI (Swagger)
- MySQL

## Вимоги для розробки

- Java 21
- Maven 3.6 або вище
- IDE (рекомендовано IntelliJ IDEA або VS Code)
- Git
- VirtualBox
- Vagrant

## Швидкий старт

### 1. Налаштування середовища розробки

1. Склонуйте репозиторій:
```bash
git clone <url-репозиторію>
cd student-management
```

2. Запустіть віртуальну машину:
```bash
vagrant up
```
Це автоматично:
- Створить Ubuntu 18.04 віртуальну машину
- Встановить Java 11, Maven, MySQL та інші залежності
- Налаштує базу даних
- Налаштує мережеві порти

### 2. Збірка та запуск проекту

1. Зберіть проект:
```bash
mvn clean install
```

2. Запустіть застосунок:
```bash
mvn spring-boot:run
```

Застосунок буде доступний за адресою: http://localhost:8080

## API Endpoints

### Студенти
- GET `/api/students` - Отримати список всіх студентів
- GET `/api/students/{id}` - Отримати студента за ID
- POST `/api/students` - Створити нового студента
- PUT `/api/students/{id}` - Оновити дані студента
- DELETE `/api/students/{id}` - Видалити студента

## Конфігурація бази даних

База даних MySQL налаштована з наступними параметрами:
- База даних: `recipes_db`
- Користувач: `recipes_user`
- Пароль: `recipes_password`
- Порт: 3307 (на хост-машині)

## Розробка

### Структура проекту
```
src/main/java/
├── com.students
│   ├── controller      # REST контролери
│   ├── dto            # Data Transfer Objects
│   ├── model          # Сутності бази даних
│   ├── repository     # Репозиторії для роботи з БД
│   └── service        # Бізнес-логіка
```

### Валідація даних
- Ім'я студента: від 2 до 50 символів
- Вік студента: від 16 до 100 років

## Документація API

Swagger UI документація доступна за адресою: http://localhost:8080/swagger-ui.html

## Віртуальна машина

### Параметри VM
- RAM: 4GB
- CPU: 2 ядра
- IP: 192.168.56.10
- Проброшені порти:
  - 8080 -> 8080 (застосунок)
  - 3306 -> 3307 (MySQL)

### Корисні команди Vagrant
```bash
vagrant up      # Запустити VM
vagrant halt    # Зупинити VM
vagrant destroy # Видалити VM
vagrant ssh     # Підключитись до VM через SSH
```

## Тестування

Для запуску тестів використовуйте:
```bash
mvn test
mvn clean test
```

## Ліцензія

Цей проект розповсюджується під MIT ліцензією. Детальніше в файлі [LICENSE](LICENSE). 