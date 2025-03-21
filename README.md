# Spring Boot Hello World Project

Цей проект демонструє базові можливості Spring Boot, включаючи:
- REST API endpoint
- Thymeleaf шаблонізацію
- Сервісний рівень
- Управління користувачами

## Вимоги

- Java 11
- Maven
- Vagrant
- VirtualBox

## Налаштування середовища розробки

1. Запустіть віртуальну машину:
```bash
vagrant up
```

2. Підключіться до віртуальної машини:
```bash
vagrant ssh
```

3. Перейдіть до директорії проекту:
```bash
cd /vagrant
```

4. Зберіть проект:
```bash
mvn clean package
```

5. Запустіть додаток:
```bash
java -jar target/spring-hello-world-0.0.1-SNAPSHOT.jar
```

## Доступні ендпоінти

- `http://localhost:8080/hello` - REST API endpoint, що повертає "Hello, World!"
- `http://localhost:8080/greet` - Сторінка привітання з використанням Thymeleaf
- `http://localhost:8080/users` - Сторінка зі списком користувачів

## Структура проекту

- `src/main/java/com/example/springhelloworld/`
  - `controller/` - Контролери
  - `model/` - Моделі даних
  - `service/` - Сервісний рівень
  - `SpringHelloWorldApplication.java` - Основний клас додатку
- `src/main/resources/templates/` - Thymeleaf шаблони
- `pom.xml` - Конфігурація Maven
- `Vagrantfile` - Конфігурація Vagrant 