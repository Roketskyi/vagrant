# Student Management System

A Spring Boot REST API application for managing students with H2 database and comprehensive logging.

## Features

- CRUD operations for students
- In-memory H2 database
- Comprehensive logging with SLF4J + Logback
- Global exception handling
- Validation
- Swagger UI documentation

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

## Running the Application

1. Clone the repository:
```bash
git clone <repository-url>
cd student-management
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Accessing H2 Console

- H2 Console URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave empty)

## API Endpoints

### Students
- GET `/api/students` - Get all students
- GET `/api/students/{id}` - Get a student by ID
- POST `/api/students` - Create a new student
- PUT `/api/students/{id}` - Update an existing student
- DELETE `/api/students/{id}` - Delete a student

## Logging

The application uses SLF4J with Logback for logging. Logs include:
- Service layer operations (CRUD operations)
- REST API requests
- Error handling
- Database operations

Log files can be found in the `logs` directory.

## Error Handling

The application includes comprehensive error handling:
- 404 Not Found - When a student is not found
- 400 Bad Request - For validation errors
- 500 Internal Server Error - For unexpected errors

## Testing

To run the tests:
```bash
mvn test
```

## Documentation

Swagger UI documentation is available at:
`http://localhost:8080/swagger-ui.html`

## Data Validation

The application includes validation for student data:
- Name: Required, 2-50 characters
- Age: Required, between 16 and 100 years

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