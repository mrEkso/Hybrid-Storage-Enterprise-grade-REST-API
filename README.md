# Hybrid Data Enterprise-grade REST API

![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-6DB33F?logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-7.0-47A248?logo=mongodb&logoColor=white)
![Azure](https://img.shields.io/badge/Azure-Blob%20Storage-0078D4?logo=microsoftazure&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Authentication-000000?logo=jsonwebtokens&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?logo=apachemaven&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green)

Enterprise-grade RESTful API service with hybrid database architecture (MySQL + MongoDB) and Azure Blob Storage integration. Built for cloud-native deployments with comprehensive security, internationalization, and testing coverage.

## Architecture

**Polyglot Persistence**  
- MySQL: relational data (users, authentication)
- MongoDB: document storage (posts, content)
- Azure Blob Storage: binary assets

**Security Layer**  
- JWT-based stateless authentication
- BCrypt password hashing
- Spring Security integration

**API Design**  
- RESTful endpoints
- i18n support (EN/UK)
- Validation layer
- Structured exception handling

## Tech Stack

| Layer | Technology |
|-------|-----------|
| **Runtime** | Java 17, Spring Boot 3.2.2 |
| **Persistence** | Spring Data JPA, Spring Data MongoDB |
| **Databases** | MySQL 8.0, MongoDB 7.0 |
| **Security** | Spring Security, JWT (jjwt) |
| **Cloud** | Azure Blob Storage SDK |
| **Build** | Maven 3.9+ |
| **Containers** | Docker, Docker Compose |
| **Testing** | JUnit 5, Spring Boot Test, Embedded MongoDB/H2 |

## Quick Start

### Prerequisites

- Docker 24.0+
- Docker Compose 2.20+
- (Optional) JDK 17 for local development
- (Optional) Maven 3.9+ for local builds

### Installation

```bash
# Clone repository
git clone https://github.com/data-in-cloud-2024/ia13-okayanchenko-dav.git
cd ia13-okayanchenko-dav

# Configure environment
cp .env-test .env

# Update Azure credentials in .env
# AZURE_ACCOUNT_NAME=your-account-name
# AZURE_ACCOUNT_KEY=your-account-key
# AZURE_BLOB_CONTAINER_NAME=your-container

# Start services
docker compose up -d --build
```

**Service availability:**
- API: `http://localhost:8080/data-in-cloud`
- MySQL: `localhost:3308`
- MongoDB: `localhost:27018`

### Verification

```bash
# Health check
curl http://localhost:8080/data-in-cloud/actuator/health

# Test authentication
curl -X POST http://localhost:8080/data-in-cloud/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123","name":"Test User"}'
```

## API Reference

### Authentication

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `POST` | `/auth/register` | Create new user account | No |
| `POST` | `/auth/login` | Authenticate user | No |
| `GET` | `/auth/logout` | Invalidate session | Yes |

### Users

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/api/v1/users` | List users (paginated) | Yes |
| `GET` | `/api/v1/users/{id}` | Get user by ID | Yes |
| `POST` | `/api/v1/users` | Create user | Yes |
| `PUT` | `/api/v1/users/{id}` | Update user | Yes |
| `DELETE` | `/api/v1/users/{id}` | Delete user | Yes |

### Posts

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `GET` | `/posts` | List all posts (paginated) | No |
| `GET` | `/posts/search?searchText=query` | Search posts | No |
| `GET` | `/posts/{id}` | Get post by ID | No |
| `GET` | `/posts/my` | Get authenticated user's posts | Yes |
| `POST` | `/posts` | Create new post | Yes |
| `PUT` | `/posts/{id}` | Update post (owner only) | Yes |
| `DELETE` | `/posts/{id}` | Delete post (owner only) | Yes |

**Authentication:** Include JWT token in `Authorization: Bearer <token>` header

## Project Structure

```
src/main/java/com/example/oss/api/
├── config/           # Spring configuration (Security, MongoDB, App)
├── controllers/      # REST endpoints
├── dto/              # Data transfer objects
├── exceptions/       # Custom exception handling
├── lang/             # Internationalization service
├── models/           # Domain entities
├── repository/       # Data access layer
├── responses/        # Response factories
├── security/         # JWT implementation
└── services/         # Business logic

src/test/java/
├── IntegrationTests/ # API integration tests
└── UnitTests/        # Unit tests
```

## Development

### Local Build (without Docker)

```bash
# Build
./mvnw clean package -DskipTests

# Run tests
./mvnw test

# Start application (requires MySQL + MongoDB running)
./mvnw spring-boot:run
```

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `DB_MYSQL_URL` | MySQL connection URL | `jdbc:mysql://mysql:3306/data-in-cloud` |
| `DB_MONGO_URL` | MongoDB connection URL | `mongodb://admin:root@mongodb:27017/data-in-cloud?authSource=admin` |
| `DB_USERNAME` | Database username | `admin` |
| `DB_PASSWORD` | Database password | `root` |
| `AZURE_BLOB_CONNECTION_STRING` | Azure Storage connection | Required |
| `AZURE_BLOB_CONTAINER_NAME` | Blob container name | `data-in-cloud` |
| `SPRING_PORT` | Application port | `8080` |

### Docker Commands

```bash
# Start all services
docker compose up -d

# View logs
docker compose logs -f spring-boot-app

# Stop services
docker compose stop

# Destroy containers and volumes
docker compose down -v

# Rebuild specific service
docker compose up -d --build spring-boot-app
```

## Testing

```bash
# Run all tests
./mvnw test

# Run integration tests only
./mvnw test -Dtest="*IntegrationTests*"

# Run unit tests only
./mvnw test -Dtest="*UnitTests*"

# Test coverage report
./mvnw test jacoco:report
```

**Test infrastructure:**
- Embedded MongoDB (Flapdoodle)
- H2 in-memory database
- Spring Security Test support

## Configuration

### Application Properties

Key configurations in `src/main/resources/application.properties`:

- **Server:** Port 8080, context path `/data-in-cloud`
- **JPA:** Auto DDL, MySQL dialect, formatted SQL
- **MongoDB:** Custom URI configuration
- **Security:** JWT secret configuration
- **Compression:** Enabled for responses

### Internationalization

Supported locales: `en` (English), `uk` (Ukrainian)

Message files: `messages_en.properties`, `messages_uk.properties`

## Security

- **Password Storage:** BCrypt hashing
- **Token Management:** JWT with HMAC-SHA256
- **Secret Key:** Configure `app.jwt.secret` in production
- **CORS:** Configured in `SecurityConfiguration`

## Production Checklist

- [ ] Replace `app.jwt.secret` with cryptographically secure value
- [ ] Configure production database credentials
- [ ] Set up Azure Blob Storage with production keys
- [ ] Enable HTTPS/TLS
- [ ] Configure application monitoring
- [ ] Set `spring.jpa.hibernate.ddl-auto=validate`
- [ ] Remove `spring.devtools` dependency
- [ ] Configure proper logging levels
- [ ] Set up database backups
- [ ] Review CORS allowed origins

## License

MIT

## Contact

Repository maintenance and style by Davyd Okaianchenko. For technical questions, open issues in this repository.

**GitHub:** [@mrEkso](https://github.com/mrEkso)  
**LinkedIn:** [Davyd Okaianchenko](https://www.linkedin.com/in/davyd-okaianchenko/)
