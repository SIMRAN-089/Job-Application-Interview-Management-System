# Job Management Platform (Advanced Backend)

This project builds on the existing Job Management codebase and adds backend infrastructure and authentication. It is not just a collection of CRUD endpoints: the existing application workflow and status transitions remain the domain core.

## Tech stack
- Java 27 (configure a real JDK 27 in IntelliJ and Maven)
- Spring Boot, Spring Web MVC, Spring Data JPA, Hibernate
- MySQL
- Spring Security + BCrypt password hashing
- JWT bearer authentication (JJWT)
- OAuth2 Client login support (provider credentials must be configured to use an actual provider)
- Swagger UI / OpenAPI
- Spring Boot Actuator and Micrometer metrics
- Maven, JUnit 5, Mockito

## Run locally
1. Install JDK 27 and MySQL. Create database `job_management` (or let the configured URL create it).
2. Set environment variables `DB_USERNAME`, `DB_PASSWORD`, and preferably `JWT_SECRET` (Base64-encoded random key of at least 32 bytes). Optional: `DB_URL`.
3. Open this folder in IntelliJ, reload Maven, then run `JobManagementApplication`.
4. Check `http://localhost:8080/swagger-ui.html` and `http://localhost:8080/actuator/health`.

## Authentication endpoints
- `POST /api/auth/register` — creates an account and hashes its password with BCrypt.
- `POST /api/auth/login` — returns a JWT. Send subsequent requests with `Authorization: Bearer <accessToken>`.

Example login JSON:
```json
{ "email": "candidate@example.com", "password": "your-password" }
```

Registration JSON uses `name`, `email`, `password`, and `role` (`CANDIDATE` or `RECRUITER`).

## OAuth2 setup
OAuth2 login support is wired through Spring Security, but a provider (Google/GitHub, etc.) is intentionally not preconfigured with fake credentials. Add a real client registration and secret in environment-backed configuration before using OAuth2 login.

## Actuator
Exposed endpoints: health, info, and metrics. The health/info endpoints are public for local checks; other actuator endpoints require the RECRUITER role. Do not expose all actuator endpoints publicly in production.

## Tests and Java 27 compatibility
Run `mvn clean test` using JDK 27. The Maven compiler release is set to 27. Stream pipelines in the existing code use `Collectors.toList()` rather than `Stream.toList()` to avoid the specific API mismatch previously encountered when the IDE/compiler targeted an older Java release. This archive has not been verified by a full build on JDK 27 in this environment.
