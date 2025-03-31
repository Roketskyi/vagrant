# Student Management System

A Spring Boot REST API application for managing students and users.

## Requirements

- Java 11 or higher
- Maven 3.6 or higher

## Building the Project

To build the project, run:

```bash
mvn clean install
```

## Running the Application

To run the application, execute:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

## API Documentation

Once the application is running, you can access the Swagger UI documentation at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI Documentation: `http://localhost:8080/api-docs`

## Available Endpoints

### Students

- `GET /api/students` - Get all students
- `GET /api/students/{id}` - Get a student by ID
- `POST /api/students` - Create a new student
- `PUT /api/students/{id}` - Update an existing student
- `DELETE /api/students/{id}` - Delete a student

## Data Validation

The application includes validation for student data:
- Name: Required, 2-50 characters
- Age: Required, between 16 and 100 years

## Error Handling

The application includes proper error handling for:
- Invalid input data (400 Bad Request)
- Resource not found (404 Not Found)
- Server errors (500 Internal Server Error)

## Testing

The application includes comprehensive test coverage using:
- JUnit 5 for unit testing
- Mockito for mocking dependencies
- Spring Boot Test for integration testing
- MockMvc for testing REST endpoints

### Test Structure

- `StudentServiceImplTest`: Tests for the student service implementation
  - Tests for getting all students
  - Tests for creating new students
  - Uses Mockito for mocking the repository layer

- `StudentRestControllerTest`: Tests for the REST controller
  - Tests for all CRUD endpoints
  - Uses MockMvc for testing HTTP requests
  - Verifies response status codes and content

### Running Tests

To run the tests, execute:

```bash
mvn test
```

This will run all unit tests and integration tests in the project. The tests will verify:
- Service layer functionality
- Controller layer endpoints
- Data validation
- Error handling
- Response formats and status codes 