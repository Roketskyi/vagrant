# Contributing to Student Management System

Thank you for your interest in contributing to the Student Management System! This document provides guidelines and instructions for contributing to the project.

## Code of Conduct

By participating in this project, you agree to abide by our Code of Conduct.

## How to Contribute

1. Fork the repository
2. Create a new branch for your feature (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Commit your changes (`git commit -m 'Add some amazing feature'`)
5. Push to the branch (`git push origin feature/amazing-feature`)
6. Open a Pull Request

## Development Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/student-management.git
   cd student-management
   ```

2. Install dependencies:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Code Style

- Follow the existing code style
- Use meaningful variable and method names
- Add comments for complex logic
- Keep methods focused and single-purpose
- Write unit tests for new features

## Testing

- Write unit tests for new features
- Ensure all tests pass before submitting a PR
- Run the test suite:
  ```bash
  mvn test
  ```

## Documentation

- Update README.md if needed
- Add Javadoc comments for public methods
- Update API documentation if endpoints change

## Pull Request Process

1. Update the README.md with details of changes if needed
2. Update the documentation with any new endpoints or changes
3. The PR will be merged once you have the sign-off of at least one other developer

## Questions?

If you have any questions, please open an issue in the GitHub repository. 