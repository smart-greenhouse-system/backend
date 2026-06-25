# Smart Greenhouse Backend

Backend service for an IoT-enabled Smart Greenhouse system. It ingests sensor data from ESP32 devices via MQTT, processes actuator commands, integrates with an external AI service for plant image analysis, and exposes a REST API for monitoring and control.

## Overview

This project provides the server-side infrastructure for a smart greenhouse. ESP32-based IoT devices publish sensor readings (temperature, humidity, soil moisture, etc.) and camera images to an MQTT broker. The backend ingests this data, stores it, and exposes it through a secure REST API. An integrated AI pipeline analyzes plant images to detect crop health, estimate harvest time, and optionally trigger automated actuator actions (e.g., turning on irrigation or ventilation) based on greenhouse configuration.

The system also maintains device registry, actuator state management, inventory tracking, and user authentication with role-based access.

## Features

- **MQTT-based IoT ingestion** -- subscribes to sensor data, camera images, and actuator state topics from ESP32 devices.
- **Sensor data processing** -- parses and stores multi-variable sensor readings with timestamps.
- **Actuator management** -- CRUD for actuators, manual execution commands, and timed (ON/OFF) execution.
- **Automatic mode** -- when enabled, AI-driven predictions can automatically trigger actuator actions with scheduled shutdown.
- **AI image analysis** -- integrates with an external Flask service to analyze plant images and return crop type, health status, confidence, and days to harvest.
- **Device registry** -- auto-registers devices on first data receipt and tracks online status.
- **Greenhouse configuration** -- configurable automatic mode toggle and AI analysis frequency.
- **User authentication** -- registration and login with JWT-based token authentication (BCrypt password hashing).
- **Role-based authorization** -- role entities (ROLE_USER, ROLE_ADMIN) for access control.
- **Inventory management** -- CRUD for greenhouse supplies with minimum threshold tracking.
- **Actuator event logging** -- immutable log of all actuator actions with origin, status, and execution confirmation.
- **Image storage** -- in-memory storage of the latest camera frame (retrievable via API).
- **REST API** -- full CRUD endpoints protected by JWT authentication.
- **OpenAPI documentation** -- auto-generated Swagger UI at `/swagger-ui.html`.
- **Dockerized deployment** -- multi-stage Docker build with docker-compose orchestration (backend + PostgreSQL).

## Architecture

The application follows a **Hexagonal Architecture** (Ports & Adapters) with domain-centric design. Each bounded context is organized as an independent module with clear separation between domain, application, and infrastructure layers.

```
                                +---------------------+
                                |    External AI       |
                                |   Flask Service      |
                                +----------+----------+
                                           |
                                        HTTP/REST
                                           |
+-----------+      MQTT       +----------+----------+        +-----------+
|   ESP32   |<--------------->|   MQTT Broker       |        |  Clients  |
|  Devices  |   invernadero/# |   (EMQX Cloud)      |        | (Web App) |
+-----------+                 +----------+----------+        +-----+-----+
                                           ^                        |
                                           |                        | HTTP/REST
                                           |                        | JWT Auth
                                           v                        v
                                +----------+-------------------------+
                                |       Smart Greenhouse Backend     |
                                |          (Spring Boot)             |
                                |                                    |
                                |  +------------------------------+  |
                                |  |      Shared / Cross-cutting  |  |
                                |  |  - MQTT Pub/Sub              |  |
                                |  |  - Security (JWT Filter)     |  |
                                |  |  - CORS                      |  |
                                |  |  - Global Error Handling     |  |
                                |  |  - Swagger / OpenAPI         |  |
                                |  +------------------------------+  |
                                |                                    |
                                |  +----------+  +----------------+  |
                                |  | Actuator |  |    Sensor      |  |
                                |  | Module   |  |    Module      |  |
                                |  +----------+  +----------------+  |
                                |  +----------+  +----------------+  |
                                |  | AI Image |  |   Prediction   |  |
                                |  | Module   |  |   Module       |  |
                                |  +----------+  +----------------+  |
                                |  +----------+  +----------------+  |
                                |  | Auth     |  |   Inventory    |  |
                                |  | Module   |  |   Module       |  |
                                |  +----------+  +----------------+  |
                                |  +----------+  +----------------+  |
                                |  | Device   |  |   Greenhouse   |  |
                                |  | Module   |  |   Config       |  |
                                |  +----------+  +----------------+  |
                                |  +----------+                      |
                                |  |  Image   |                      |
                                |  |  Module  |                      |
                                |  +----------+                      |
                                +----+--------+--------+-------------+
                                     |        |        |
                                     v        v        v
                                +----+--------++-------++-----------+
                                | PostgreSQL  | MongoDB | In-Memory |
                                | (auth:      | (IoT    | (image    |
                                |  users,     |  data,  |  storage) |
                                |  roles)     |  devices|           |
                                +-------------+---------+-----------+
```

### Module Breakdown

Each module is self-contained and follows the same internal structure:

- **domain/** -- enterprise business logic: models (immutable), repository ports (interfaces), domain services.
- **application/** -- use cases (orchestrating domain logic) and commands (input DTOs for use cases).
- **infrastructure/** -- adapters: inbound (REST controllers, MQTT handlers), outbound (persistence, HTTP clients), mappers.
- **dto/** -- request and response objects for the API layer.

### MQTT Topic Routing

The `MqttMessageListener` receives all messages from the `invernadero/#` subscription and routes them to the appropriate handler based on topic pattern:

| Topic Pattern | Handler | Purpose |
|---------------|---------|---------|
| `invernadero/{deviceId}/sensores` | `SensorMessageHandler` | Parse and store sensor readings |
| `invernadero/cam/imagen` | `ImageMessageHandler` | Receive camera image and trigger AI analysis |
| `invernadero/{deviceId}/actuadores/{actuador}/estado` | `ActuatorStateMessageHandler` | Process actuator state confirmations |

Outbound publishing uses topic `invernadero/{deviceId}/actuadores/{actuador}/cmd` to send ON/OFF commands to devices.

## Technologies

### Language and Framework

| Technology | Version |
|------------|---------|
| Java | 17 |
| Spring Boot | 4.0.5 |
| Spring Web MVC | -- |
| Spring Security | -- |
| Spring Data JPA | -- |
| Spring Data MongoDB | -- |
| Spring Integration MQTT | -- |
| Gradle | 9.4.1 |

### Libraries

| Library | Purpose |
|---------|---------|
| Eclipse Paho MQTT Client 1.2.5 | MQTT connectivity |
| JJWT 0.12.6 | JWT token generation and validation |
| MapStruct 1.5.5 | Object mapping between domain, entities, and DTOs |
| Lombok | Boilerplate reduction |
| Springdoc OpenAPI 3.0.3 | OpenAPI 3.0 and Swagger UI |
| H2 (test) | In-memory database for testing |
| PostgreSQL | Production user database |
| MongoDB Atlas | IoT data persistence |

### Infrastructure

| Tool | Purpose |
|------|---------|
| Docker | Containerization |
| Docker Compose | Multi-service orchestration |
| EMQX Cloud | Managed MQTT broker |
| MongoDB Atlas | Managed document database |

## Project Structure

```
src/main/java/com/proyectosu/invernadero/
|-- InvernaderoApplication.java          # Spring Boot entry point
|
|-- actuator/                            # Actuator management and execution
|   |-- domain/model/                    # Actuator, ActuatorEvent, ActuatorTarget
|   |-- domain/port/                     # Repository and resolver interfaces
|   |-- application/usecases/            # CRUD + execute + timed execute + state processing
|   |-- infrastructure/inbound/          # ActuatorController, ActuatorEventController
|   |-- infrastructure/inbound/mqtt/     # ActuatorStateMessageHandler
|   |-- infrastructure/outbound/         # MongoDB persistence adapters
|
|-- ai/                                  # External AI service integration
|   |-- domain/model/                    # AiImagePredictionResult
|   |-- domain/port/                     # AiPredictionClientPort
|   |-- application/usecases/            # AnalyzeImageUseCase
|   |-- infrastructure/outbound/http/    # FlaskAiPredictionClientAdapter (RestTemplate)
|
|-- auth/                                # Authentication and authorization
|   |-- domain/model/                    # User, Role, UserStatus
|   |-- domain/ports/                    # JwtServicePort, PasswordEncoderPort, repository ports
|   |-- application/usecase/             # LoginUserUseCase, RegisterUserUseCase
|   |-- inbound/controller/              # AuthController
|   |-- infraestructure/persistence/     # JPA entities, repositories, adapters, RoleSeeder
|   |-- infraestructure/security/        # JwtServiceAdapter, BCryptPasswordEncoderAdapter
|
|-- device/                              # Device registry
|   |-- domain/model/                    # Device (ESP32)
|   |-- application/usecases/            # GetDevicesUseCase, UpsertDeviceStatusUseCase
|   |-- infrastructure/inbound/          # DeviceController
|   |-- infrastructure/outbound/         # MongoDB persistence adapters
|
|-- greenhouse/                          # Greenhouse configuration
|   |-- domain/model/                    # GreenhouseConfig (automaticMode, frequency)
|   |-- application/usecases/            # Get and update config
|   |-- infrastructure/inbound/          # GreenhouseConfigController
|   |-- infrastructure/outbound/         # MongoDB persistence adapters
|
|-- image/                               # Camera image handling
|   |-- domain/model/                    # GreenhouseImage
|   |-- application/usecases/            # Store and retrieve latest image
|   |-- infrastructure/inbound/mqtt/     # ImageMessageHandler
|   |-- infrastructure/outbound/         # InMemoryImageStorageAdapter
|
|-- inventory/                           # Supply inventory
|   |-- domain/model/                    # InventoryItem
|   |-- application/usecases/            # CRUD operations
|   |-- infrastructure/inbound/          # InventoryController
|   |-- infrastructure/outbound/         # MongoDB persistence adapters
|
|-- prediction/                          # AI-driven predictions
|   |-- domain/model/                    # Prediction (image analysis + actuator requests)
|   |-- domain/port/                     # PredictionRepository, GreenhouseConfigReader, TimedActuatorExecutor
|   |-- application/usecases/            # Process AI prediction, save/retrieve image analysis
|   |-- infrastructure/inbound/          # PredictionController
|   |-- infrastructure/outbound/         # MongoDB persistence adapters
|
|-- sensor/                              # Sensor data processing
|   |-- domain/model/                    # SensorData
|   |-- application/usecases/            # Save, get latest, get history
|   |-- infrastructure/inbound/          # SensorController
|   |-- infrastructure/inbound/mqtt/     # SensorMessageHandler
|   |-- infrastructure/outbound/         # MongoDB persistence adapters
|
|-- shared/                              # Cross-cutting infrastructure
    |-- config/                          # App wiring, CORS, AI client, OpenAPI, TaskScheduler
    |-- config/mqtt/                     # MQTT publisher and subscriber configuration
    |-- domain/ports/                    # MqttPublisherPort interface
    |-- errores/                         # Global exception handler and custom exceptions
    |-- infrastructure/mqtt/             # MqttPublisherAdapter, MqttMessageListener
    |-- security/                        # SecurityConfig, JwtAuthenticationFilter
```

## Security

### Authentication

Authentication is handled via **JWT (JSON Web Tokens)** using the JJWT library with HMAC-SHA256 signing.

- Users register with an email and password (minimum 8 characters).
- Passwords are hashed using **BCrypt** via Spring Security's `BCryptPasswordEncoder`.
- On login, the server validates credentials and returns a signed JWT token (1-hour expiration).
- The token contains the user's email (subject), user ID, and assigned roles.

### Authorization

A `JwtAuthenticationFilter` (extending `OncePerRequestFilter`) intercepts every request:

1. Extracts the `Authorization: Bearer <token>` header.
2. Validates the token signature and expiration.
3. Extracts roles from the token and populates the Spring Security `SecurityContext`.

**Public endpoints** (no token required):
- `POST /api/auth/register` and `POST /api/auth/login`
- `POST /api/predictions`
- `GET /api/eventos`
- Swagger UI and OpenAPI docs paths

**All other endpoints** require a valid JWT token.

CORS is enabled for configured origins, and CSRF protection is disabled for the REST API.

### Password Policy

Passwords must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit (enforced by regex in the `User` domain model).

## Database

The project uses a **dual-database** strategy:

### PostgreSQL (via Spring Data JPA / Hibernate)

Stores user authentication and authorization data. Schema is auto-managed via `ddl-auto: update`.

**Tables:**

| Table | Columns | Notes |
|-------|---------|-------|
| `users` | `id` (UUID PK), `email` (unique), `password`, `status`, `created_at` | User accounts |
| `roles` | `id` (BIGINT PK), `name` (unique) | Roles: ROLE_USER, ROLE_ADMIN |
| `user_roles` | `user_id` (FK), `role_id` (FK) | Many-to-many join table |

### MongoDB Atlas (via Spring Data MongoDB)

Stores all IoT and operational data.

**Collections:**

| Collection | Key Fields | Purpose |
|------------|------------|---------|
| `sensor_data` | `deviceId`, `sensores` (Map), `createdAt` | Time-series sensor readings |
| `actuators` | `actuatorId`, `deviceId`, `actuador`, `estado`, `enabled` | Physical actuator state |
| `actuator_events` | `deviceId`, `actuator`, `action`, `origin`, `executed` | Immutable actuator action log |
| `devices` | `deviceId`, `nombre`, `tipo`, `estado`, `sensores`, `actuadores`, `lastSeen` | Device registry |
| `predictions` | `tipo`, `deviceId`, `cultivo`, `confianza`, `executed` | AI prediction results |
| `greenhouse_config` | `greenhouseName`, `automaticMode`, `aiAnalysisFrequencyMinutes` | Configuration singleton |
| `inventory` | `nombre`, `cantidad`, `unidad`, `thresholdMinimo` | Supply inventory |

## Communication

### MQTT

MQTT is the primary protocol for bidirectional communication with IoT devices.

- **Broker:** EMQX Cloud (managed, TLS over port 8883)
- **Client ID:** `backend-invernadero`
- **QoS:** 1 (at least once delivery)
- **Subscription:** `invernadero/#` (all sub-topics)
- **Authentication:** Username/password

**Inbound topics (device to backend):**

| Topic | Payload | Trigger |
|-------|---------|---------|
| `invernadero/{device_id}/sensores` | `{"device_id": "...", "sensores": {"temperatura": 25.5, ...}}` | Periodic sensor readings |
| `invernadero/cam/imagen` | `{"device_id": "...", "formato": "...", "resolucion": "...", "imagen": "<base64>"}` | Camera capture |
| `invernadero/{device_id}/actuadores/{actuador}/estado` | `{"device_id": "...", "actuador": "...", "estado": "ON", "ejecutado": true}` | Actuator state confirmation |

**Outbound topics (backend to device):**

| Topic | Payload | Purpose |
|-------|---------|---------|
| `invernadero/{device_id}/actuadores/{actuador}/cmd` | `{"accion": "ON"}` | Send command to actuator |

Spring Integration MQTT with Eclipse Paho handles the connection lifecycle, message conversion, and channel routing.

### HTTP / REST

All API endpoints communicate over HTTP. The REST API serves as the interface for web and mobile clients.

### External AI Service

An external Flask-based AI microservice is called via HTTP POST for plant image analysis:

- **Endpoint:** `POST {ai.base-url}/predict/api/predict`
- **Request body:** `{"imageBase64": "<base64-encoded-image>"}`
- **Response:** `{"cultivo": "...", "success": true, "estadoPlanta": "...", "confianza": 0.95, "tiempoCosechaDias": 15}`

### Task Scheduling

A `ThreadPoolTaskScheduler` (pool size 5) is used to schedule delayed actuator OFF commands after AI-triggered ON commands, enabling timed irrigation or ventilation cycles.

## API Documentation

Interactive API documentation is available via **Swagger UI** when the server is running:

- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI spec:** `http://localhost:8080/v3/api-docs`

### Endpoint Summary

#### Authentication (public)

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT token |

#### Predictions (public)

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/predictions` | Create an AI prediction (optionally triggers actuator) |

#### Actuators (authenticated)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/actuators` | List all actuators |
| POST | `/api/actuators` | Create an actuator |
| PATCH | `/api/actuators/{id}` | Update an actuator |
| DELETE | `/api/actuators/{id}` | Delete an actuator |
| POST | `/api/actuators/execute` | Manually execute an actuator |
| GET | `/api/actuator-events` | List all actuator events |
| GET | `/api/actuator-events/{deviceId}` | List events for a device |

#### Sensors (authenticated)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/sensors/latest` | Get latest sensor readings |
| GET | `/api/sensors/history/{deviceId}` | Get sensor history for a device |

#### Devices (authenticated)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/devices` | List all registered devices |

#### Greenhouse Configuration (authenticated)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/config` | Get greenhouse configuration |
| PATCH | `/api/config` | Update greenhouse configuration |

#### Inventory (authenticated)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/inventory` | List inventory items |
| POST | `/api/inventory` | Create inventory item |
| PATCH | `/api/inventory/{id}` | Update inventory item |

#### Image Analysis Predictions (authenticated)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/predictions/latest-image-analysis` | Get latest image analysis result |
| GET | `/api/predictions/image-analysis` | Get all image analysis results |

## Installation

### Prerequisites

- JDK 17
- Gradle 9.4.1 (or use the provided wrapper)
- Docker and Docker Compose (for containerized deployment)
- MongoDB Atlas account (or local MongoDB)
- EMQX Cloud account (or any MQTT broker)
- PostgreSQL 16 (for production without Docker)

### Clone

```
git clone <repository-url>
cd backend
```

## Configuration

Configuration is managed through Spring profiles and environment variables.

### Profile: `dev` (default)

The dev profile uses `application-dev.yml` with inline configuration for local development. Override any value via environment variables.

### Profile: `prod`

The prod profile reads all configuration from environment variables:

| Variable | Description |
|----------|-------------|
| `SPRING_DATASOURCE_URL` | PostgreSQL JDBC URL |
| `DB_USER` | PostgreSQL username |
| `DB_PASSWORD` | PostgreSQL password |
| `MONGO_URI` | MongoDB connection string |
| `JWT_SECRET` | HMAC-SHA key for JWT signing |
| `MQTT_URL` | MQTT broker URL (e.g., `ssl://host:8883`) |
| `MQTT_CLIENT_ID` | MQTT client identifier |
| `MQTT_USERNAME` | MQTT broker username |
| `MQTT_PASSWORD` | MQTT broker password |
| `MQTT_TOPIC_IN` | MQTT subscription topic (default: `invernadero/#`) |
| `AI_BASE_URL` | Flask AI service base URL |
| `CORS_ALLOWED_ORIGINS` | Comma-separated allowed CORS origins |

### .env file

A `.env` file is used by Docker Compose to inject environment variables into containers. Copy `.env.prod` and fill in your values.

## Running the Project

### Locally with Gradle

```bash
# Development mode (auto-restart with DevTools)
./gradlew bootRun

# Build and run JAR
./gradlew clean build
java -jar build/libs/invernadero-0.0.1-SNAPSHOT.jar
```

The server starts on port 8080 by default. Ensure PostgreSQL and MongoDB are accessible before starting.

### With Docker Compose

```bash
docker-compose up --build
```

This starts two containers:
- `invernadero-backend` (Spring Boot application on port 8080)
- `invernadero-postgres` (PostgreSQL 16 on port 5432)

Both share a custom bridge network `invernadero-network`. The backend waits for PostgreSQL to pass its health check before starting.

### Testing

Run tests with:

```bash
./gradlew test
```

Tests use the `test` profile which configures H2 (PostgreSQL-compatible mode) for JPA tests, a local MongoDB connection, and a dummy MQTT broker address. The MQTT inbound adapter is disabled during tests to prevent connection attempts.

## Deployment

The project is containerized and ready for deployment on any container orchestration platform.

1. Set up production environment variables (or a `.env` file).
2. Build and run with Docker Compose:
   ```bash
   docker-compose -f docker-compose.yml up -d --build
   ```
3. Alternatively, build the image and push to a registry:
   ```bash
   docker build -t invernadero-backend .
   docker tag invernadero-backend <registry>/invernadero-backend:latest
   docker push <registry>/invernadero-backend:latest
   ```

The Dockerfile uses a multi-stage build:
- **Stage 1 (build):** `gradle:8.14-jdk17` compiles the project and packages the JAR (tests are skipped).
- **Stage 2 (runtime):** `eclipse-temurin:17-jdk` runs the JAR with minimal footprint.

## Future Improvements

- **Unit and integration tests** -- cover use cases, repositories, and controllers with automated tests.
- **PostgreSQL for predictions** -- migrate prediction storage to PostgreSQL for relational querying and reporting.
- **Persistent image storage** -- replace in-memory image storage with S3-compatible object storage.
- **WebSocket support** -- push real-time sensor updates and actuator events to web clients.
- **Rate limiting** -- protect the API from abuse with rate limiting per user/IP.
- **Audit logging** -- comprehensive audit trail for all administrative actions.
- **Secrets management** -- integrate with Vault or cloud-native secret managers instead of environment files.
- **CI/CD pipeline** -- automated build, test, and deployment with GitHub Actions or similar.
- **Sensor data aggregation** -- time-window aggregation for historical queries and dashboard rendering.
- **Multi-greenhouse support** -- extend the config and device models to support multiple independent greenhouse installations.

---

Desarrollado por [Daniel Felipe Ordoñez Amaya](https://github.com/ve312)
