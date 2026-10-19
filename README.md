# Security Asymmetric Encryption

A Spring Boot REST API that demonstrates JWT-based authentication using asymmetric RSA keys and Spring Security. The project includes user registration, login, token refresh, profile management, and database-backed persistence with Microsoft SQL Server.

## Features

- User registration and authentication
- JWT access tokens and refresh tokens
- RSA public/private key signing for token validation
- Spring Security with stateless session handling
- Email validation with disposable-email checks
- Swagger/OpenAPI documentation
- Dockerized SQL Server for local development

## Tech Stack

- Java 25
- Spring Boot 4.1.1
- Spring Security
- Spring Data JPA
- Microsoft SQL Server
- JJWT (JWT library)
- Springdoc OpenAPI
- Gradle

## Project Structure

```text
security-asymmetric-encryption/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/security_asymmetric_encryption/
│   │   └── resources/
│   │       ├── application.yaml
│   │       └── keys/
│   │           └── local-only/
│   │               ├── private_key.pem
│   │               └── public_key.pem
├── .env.example
├── build.gradle
├── docker-compose.yaml
├── gradlew
├── settings.gradle
└── README.md
```

## Prerequisites

- Java 25 or a compatible JDK
- Docker Desktop (for SQL Server container)
- Git

## Configuration

1. Copy the example environment file:

```bash
copy .env.example .env
```

2. Update the environment values in `.env`:

```env
DB_URL=localhost
DB_PORT=1434
DB_NAME=TechEducation_DB
DB_USERNAME=sa
DB_PASSWORD=sa@12345678
```

The application reads these values from `.env` and uses them in `application.yaml`.

## Running the Database

Start the local SQL Server instance with Docker Compose:

```bash
docker compose up -d
```

This project uses the container defined in `docker-compose.yaml` and exposes SQL Server on port `1434`.

## Running the Application

Use the Gradle wrapper to start the API:

```bash
./gradlew bootRun
```

On Windows PowerShell:

```powershell
./gradlew.bat bootRun
```

The application starts on:

- http://localhost:8081

## API Endpoints

### Authentication

- `POST /api/v1/auth/register` - Create a new user account
- `POST /api/v1/auth/login` - Authenticate and receive JWT tokens
- `POST /api/v1/auth/refresh-token` - Refresh an access token using a refresh token

### User APIs

- `PATCH /api/v1/users/me` - Update profile information
- `POST /api/v1/users/me/password` - Change password
- `PATCH /api/v1/users/me/deactivate` - Deactivate account
- `PATCH /api/v1/users/me/reactivate` - Reactivate account
- `DELETE /api/v1/users/me` - Delete current account

### Swagger UI

Open the Swagger documentation here:

- http://localhost:8081/swagger-ui/index.html

## JWT and Key Setup

JWT signing is handled with RSA keys loaded from:

- `src/main/resources/keys/local-only/private_key.pem`
- `src/main/resources/keys/local-only/public_key.pem`

These keys are used to sign and validate access and refresh tokens.

## Notes

- The application is configured as a stateless REST API, so JWTs are used instead of server-side sessions.
- Access tokens are short-lived; refresh tokens are used to obtain new access tokens.
- The default database connection uses `trustServerCertificate=true` for local development.

## License

This project is provided as a sample/security demo and is intended for educational and local-development use.
