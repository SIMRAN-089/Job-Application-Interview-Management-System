# Job Management System

A secure recruitment backend built with Java and Spring Boot to manage job postings, candidate applications, and recruitment workflows.

## Tech Stack

Java 21 · Spring Boot · Spring Security · JWT · MySQL · Spring Data JPA · Maven · Swagger/OpenAPI · Spring Boot Actuator · JUnit 5 · Mockito

## Features

- JWT-based authentication and role-based access control
- Candidate and recruiter roles
- Job listing and management
- Job applications and status tracking
- Duplicate application prevention
- Input validation and exception handling
- Swagger UI API documentation
- Actuator health checks and application metrics
- Unit testing with JUnit and Mockito

## Architecture

The application follows a layered backend architecture:

- **Controller:** Handles HTTP requests and responses
- **Service:** Implements business logic
- **Repository:** Manages database operations
- **Entity and DTO:** Represent database models and API data
- **Security:** Handles authentication and authorization

## Screenshots

_Add screenshots of the actual Swagger UI and API responses here._

## Getting Started

### Prerequisites
- Java 21
- MySQL 8
- Maven

### Configuration

Create the `job_management` database and configure these environment variables:

```text
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
JWT_SECRET=your_base64_encoded_random_secret
```

Optionally configure `DB_URL` if your MySQL connection URL differs from the default.

### Run the Application

1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Reload the Maven project.
4. Configure the required environment variables.
5. Run `JobManagementApplication`.

### API Documentation

Once the application is running, open:

- Swagger UI: http://localhost:8080/swagger-ui.html
- Health: http://localhost:8080/actuator/health
- Info: http://localhost:8080/actuator/info
- Metrics: http://localhost:8080/actuator/metrics

## Key API Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/auth/register` | Register a user |
| POST | `/api/auth/login` | Authenticate and receive a JWT |
| GET | `/api/jobs` | Retrieve job listings |
| POST | `/api/applications` | Apply for a job |
| GET | `/api/applications/job/{jobId}` | View applications for a job |
| PUT | `/api/applications/{id}/status` | Update application status |

Protected endpoints require a valid JWT Bearer token and the appropriate role.

## Testing

Run the test suite using:

```bash
mvn clean test
```

## Security

Passwords are hashed using BCrypt, and JWT authentication protects secured endpoints. Keep database credentials and JWT secrets out of version control.