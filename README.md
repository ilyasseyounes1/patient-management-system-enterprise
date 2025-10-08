# 🚀 Java Spring Microservices Architecture

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=spring-boot)
![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-3.x-black?style=for-the-badge&logo=apache-kafka)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?style=for-the-badge&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker)
![gRPC](https://img.shields.io/badge/gRPC-1.69-00ADD8?style=for-the-badge&logo=google)

**A production-ready microservices architecture built with Spring Boot, demonstrating modern cloud-native patterns and best practices**

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Architecture](#-architecture)
- [Key Features](#-key-features)
- [Tech Stack](#-tech-stack)
- [Services](#-services)
- [Prerequisites](#-prerequisites)
- [Quick Start](#-quick-start)
- [Configuration](#-configuration)
- [API Documentation](#-api-documentation)
- [Development](#-development)
- [Testing](#-testing)
- [Deployment](#-deployment)
- [Monitoring](#-monitoring)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🎯 Overview

This project showcases a comprehensive microservices architecture using Spring Boot and Spring Cloud ecosystem. It demonstrates key patterns including service discovery, API gateway, inter-service communication via gRPC and Kafka, distributed authentication, and containerization.

### 🎥 Tutorial Series

This codebase is part of a comprehensive YouTube tutorial series on building production-ready microservices. Join our [Discord community](https://discord.com) for help, discussions, and updates!

---

## 🏗 Architecture

```mermaid
graph TB
    subgraph ClientLayer["Client Layer"]
        Client["🌐 Client Applications"]
    end
    
    subgraph GatewayLayer["API Gateway Layer"]
        Gateway["🚪 API Gateway"]
    end
    
    subgraph AuthLayer["Authentication"]
        Auth["🔐 Auth Service"]
        AuthDB[("Auth DB")]
    end
    
    subgraph CoreServices["Core Services"]
        Patient["👥 Patient Service"]
        Billing["💰 Billing Service"]
        PatientDB[("Patient DB")]
    end
    
    subgraph MessageBroker["Message Broker"]
        Kafka["📨 Apache Kafka"]
    end
    
    subgraph ServiceDiscovery["Service Discovery"]
        Eureka["🔍 Eureka Server"]
    end
    
    Client --> Gateway
    Gateway --> Auth
    Gateway --> Patient
    Gateway --> Billing
    
    Auth --> AuthDB
    Patient --> PatientDB
    Patient --> Kafka
    Billing --> Kafka
    Patient -.gRPC.-> Billing
    
    Auth -.Register.-> Eureka
    Patient -.Register.-> Eureka
    Billing -.Register.-> Eureka
    Gateway -.Discover.-> Eureka
    
    style Client fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style Gateway fill:#F5A623,stroke:#C17D11,stroke-width:2px,color:#000
    style Auth fill:#E74C3C,stroke:#A93226,stroke-width:2px,color:#fff
    style Patient fill:#2ECC71,stroke:#229954,stroke-width:2px,color:#fff
    style Billing fill:#9B59B6,stroke:#6C3483,stroke-width:2px,color:#fff
    style Kafka fill:#E91E63,stroke:#AD1457,stroke-width:2px,color:#fff
    style Eureka fill:#3498DB,stroke:#21618C,stroke-width:2px,color:#fff
    style AuthDB fill:#34495E,stroke:#1C2833,stroke-width:2px,color:#fff
    style PatientDB fill:#34495E,stroke:#1C2833,stroke-width:2px,color:#fff
```

### Communication Patterns

```mermaid
sequenceDiagram
    participant C as Client
    participant G as API Gateway
    participant A as Auth Service
    participant P as Patient Service
    participant B as Billing Service
    participant K as Kafka

    C->>G: HTTP Request + JWT
    G->>A: Validate Token
    A-->>G: Token Valid
    G->>P: Forward Request
    P->>B: gRPC Call (Get Billing Info)
    B-->>P: Billing Data
    P->>K: Publish Event
    K-->>B: Consume Event
    P-->>G: Response
    G-->>C: HTTP Response
```

---

## ✨ Key Features

### 🔐 Security & Authentication
- **JWT-based authentication** with Spring Security
- **Role-based access control** (RBAC)
- **Secure inter-service communication** via gRPC
- **Password encryption** using BCrypt

### 🔄 Communication
- **Synchronous**: REST APIs and gRPC
- **Asynchronous**: Apache Kafka event streaming
- **Protocol Buffers** for efficient data serialization

### 🗄️ Data Management
- **PostgreSQL** for persistent storage
- **H2** for testing environments
- **JPA/Hibernate** for ORM
- **Database migrations** with SQL initialization

### 🐳 DevOps & Deployment
- **Docker Compose** for local development
- **Multi-container orchestration**
- **Remote debugging** support
- **Environment-based configuration**

### 📊 API & Documentation
- **OpenAPI/Swagger** documentation
- **RESTful API design** principles
- **Versioned APIs**

---

## 🛠 Tech Stack

### Backend Framework
| Technology | Version | Purpose |
|------------|---------|---------|
| ![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-6DB33F?logo=spring-boot&logoColor=white) | 3.x | Core framework |
| ![Spring Cloud](https://img.shields.io/badge/-Spring%20Cloud-6DB33F?logo=spring&logoColor=white) | Latest | Microservices toolkit |
| ![Java](https://img.shields.io/badge/-Java-007396?logo=java&logoColor=white) | 17+ | Programming language |

### Communication
| Technology | Version | Purpose |
|------------|---------|---------|
| ![gRPC](https://img.shields.io/badge/-gRPC-00ADD8?logo=google&logoColor=white) | 1.69 | RPC framework |
| ![Kafka](https://img.shields.io/badge/-Apache%20Kafka-231F20?logo=apache-kafka&logoColor=white) | 3.x | Event streaming |
| ![Protocol Buffers](https://img.shields.io/badge/-Protobuf-00599C?logo=google&logoColor=white) | 4.29 | Data serialization |

### Data & Storage
| Technology | Version | Purpose |
|------------|---------|---------|
| ![PostgreSQL](https://img.shields.io/badge/-PostgreSQL-336791?logo=postgresql&logoColor=white) | 15+ | Primary database |
| ![H2](https://img.shields.io/badge/-H2-0000BB?logo=h2&logoColor=white) | Latest | Test database |
| ![Hibernate](https://img.shields.io/badge/-Hibernate-59666C?logo=hibernate&logoColor=white) | Latest | ORM framework |

### Security
| Technology | Version | Purpose |
|------------|---------|---------|
| ![Spring Security](https://img.shields.io/badge/-Spring%20Security-6DB33F?logo=spring-security&logoColor=white) | 6.x | Security framework |
| ![JWT](https://img.shields.io/badge/-JWT-000000?logo=json-web-tokens&logoColor=white) | 0.12.6 | Token-based auth |

### DevOps & Tools
| Technology | Version | Purpose |
|------------|---------|---------|
| ![Docker](https://img.shields.io/badge/-Docker-2496ED?logo=docker&logoColor=white) | Latest | Containerization |
| ![Maven](https://img.shields.io/badge/-Maven-C71A36?logo=apache-maven&logoColor=white) | 3.x | Build tool |
| ![Swagger](https://img.shields.io/badge/-Swagger-85EA2D?logo=swagger&logoColor=black) | 2.6.0 | API documentation |

---

## 🎯 Services

### 🔐 Auth Service
**Port:** `8080`

Handles user authentication and authorization.

**Features:**
- JWT token generation and validation
- User registration and login
- Role-based access control
- Secure password storage

**Endpoints:**
```
POST   /api/auth/register  - Register new user
POST   /api/auth/login     - User login
GET    /api/auth/validate  - Validate JWT token
POST   /api/auth/refresh   - Refresh access token
```

**Default User:**
```
Email: testuser@test.com
Password: test123
Role: ADMIN
```

---

### 👥 Patient Service
**Port:** `8081`

Manages patient records and medical information.

**Features:**
- CRUD operations for patients
- Integration with Billing Service via gRPC
- Event publishing to Kafka
- PostgreSQL persistence

**Endpoints:**
```
GET    /api/patients       - Get all patients
GET    /api/patients/{id}  - Get patient by ID
POST   /api/patients       - Create new patient
PUT    /api/patients/{id}  - Update patient
DELETE /api/patients/{id}  - Delete patient
```

---

### 💰 Billing Service
**Port:** `8082` (HTTP) | `9005` (gRPC)

Handles billing operations and payment processing.

**Features:**
- gRPC server for inter-service calls
- Kafka event consumption
- Billing calculations and invoicing

**gRPC Methods:**
```protobuf
service BillingService {
  rpc GetBilling(BillingRequest) returns (BillingResponse);
  rpc CreateInvoice(InvoiceRequest) returns (InvoiceResponse);
}
```

---

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- ☕ **Java JDK 17+** - [Download](https://adoptium.net/)
- 🔨 **Maven 3.8+** - [Download](https://maven.apache.org/download.cgi)
- 🐳 **Docker & Docker Compose** - [Download](https://www.docker.com/products/docker-desktop)
- 💻 **IDE** - IntelliJ IDEA (recommended) or Eclipse
- 📝 **Git** - [Download](https://git-scm.com/downloads)

**Verify Installation:**
```bash
java -version    # Should show Java 17+
mvn -version     # Should show Maven 3.8+
docker --version # Should show Docker 20+
```

---

## 🚀 Quick Start

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/chrisblakely01/java-spring-microservices.git
cd java-spring-microservices
```

### 2️⃣ Build All Services

```bash
# Build all microservices
mvn clean install -DskipTests

# Or build individual services
cd auth-service && mvn clean install
cd ../patient-service && mvn clean install
cd ../billing-service && mvn clean install
```

### 3️⃣ Start Infrastructure with Docker Compose

```bash
# Start PostgreSQL, Kafka, and all services
docker-compose up -d

# View logs
docker-compose logs -f

# Check running containers
docker ps
```

### 4️⃣ Verify Services

```bash
# Check Auth Service
curl http://localhost:8080/actuator/health

# Check Patient Service
curl http://localhost:8081/actuator/health

# Check Billing Service
curl http://localhost:8082/actuator/health
```

### 5️⃣ Access API Documentation

Open your browser and navigate to:

- **Auth Service:** http://localhost:8080/swagger-ui.html
- **Patient Service:** http://localhost:8081/swagger-ui.html
- **Billing Service:** http://localhost:8082/swagger-ui.html

---

## ⚙️ Configuration

### Environment Variables

#### Auth Service

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://auth-service-db:5432/db
SPRING_DATASOURCE_USERNAME=admin_user
SPRING_DATASOURCE_PASSWORD=password
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_SQL_INIT_MODE=always
```

#### Patient Service

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://patient-service-db:5432/db
SPRING_DATASOURCE_USERNAME=admin_user
SPRING_DATASOURCE_PASSWORD=password
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092
SPRING_SQL_INIT_MODE=always
BILLING_SERVICE_ADDRESS=billing-service
BILLING_SERVICE_GRPC_PORT=9005
```

#### Kafka Configuration

```bash
KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092,EXTERNAL://localhost:9094
KAFKA_CFG_CONTROLLER_LISTENER_NAMES=CONTROLLER
KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=0@kafka:9093
KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,EXTERNAL:PLAINTEXT,PLAINTEXT:PLAINTEXT
KAFKA_CFG_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093,EXTERNAL://:9094
KAFKA_CFG_NODE_ID=0
KAFKA_CFG_PROCESS_ROLES=controller,broker
```

### Application Properties

#### Kafka Consumer (Patient/Billing Service)

```properties
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.ByteArrayDeserializer
spring.kafka.bootstrap-servers=kafka:9092
```

---

## 📚 API Documentation

### Authentication Flow

```mermaid
sequenceDiagram
    participant User
    participant AuthService
    participant Database
    participant JWT

    User->>AuthService: POST /api/auth/register
    AuthService->>Database: Save User (encrypted password)
    Database-->>AuthService: User Created
    AuthService-->>User: Success Response

    User->>AuthService: POST /api/auth/login
    AuthService->>Database: Verify Credentials
    Database-->>AuthService: User Data
    AuthService->>JWT: Generate Token
    JWT-->>AuthService: Access Token + Refresh Token
    AuthService-->>User: Tokens
```

### Example API Calls

#### Register User

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "SecurePass123!",
    "role": "USER"
  }'
```

#### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "testuser@test.com",
    "password": "test123"
  }'
```

#### Create Patient (Authenticated)

```bash
curl -X POST http://localhost:8081/api/patients \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "dateOfBirth": "1990-01-01",
    "email": "john.doe@example.com"
  }'
```

---

## 👨‍💻 Development

### Project Structure

```
java-spring-microservices/
├── auth-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/healthcare/auth/
│   │   │   │       ├── config/
│   │   │   │       ├── controller/
│   │   │   │       ├── entity/
│   │   │   │       ├── repository/
│   │   │   │       ├── service/
│   │   │   │       └── security/
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── data.sql
│   │   └── test/
│   └── pom.xml
├── patient-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   ├── proto/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── billing-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   ├── proto/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── docker-compose.yml
└── README.md
```

### Adding gRPC Dependencies

Add to `pom.xml`:

```xml
<dependencies>
    <!-- gRPC -->
    <dependency>
        <groupId>io.grpc</groupId>
        <artifactId>grpc-netty-shaded</artifactId>
        <version>1.69.0</version>
    </dependency>
    <dependency>
        <groupId>io.grpc</groupId>
        <artifactId>grpc-protobuf</artifactId>
        <version>1.69.0</version>
    </dependency>
    <dependency>
        <groupId>io.grpc</groupId>
        <artifactId>grpc-stub</artifactId>
        <version>1.69.0</version>
    </dependency>
    <dependency>
        <groupId>net.devh</groupId>
        <artifactId>grpc-spring-boot-starter</artifactId>
        <version>3.1.0.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>com.google.protobuf</groupId>
        <artifactId>protobuf-java</artifactId>
        <version>4.29.1</version>
    </dependency>
    <dependency>
        <groupId>org.apache.tomcat</groupId>
        <artifactId>annotations-api</artifactId>
        <version>6.0.53</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

### Build Configuration for Protobuf

```xml
<build>
    <extensions>
        <extension>
            <groupId>kr.motd.maven</groupId>
            <artifactId>os-maven-plugin</artifactId>
            <version>1.7.0</version>
        </extension>
    </extensions>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
        <plugin>
            <groupId>org.xolstice.maven.plugins</groupId>
            <artifactId>protobuf-maven-plugin</artifactId>
            <version>0.6.1</version>
            <configuration>
                <protocArtifact>com.google.protobuf:protoc:3.25.5:exe:${os.detected.classifier}</protocArtifact>
                <pluginId>grpc-java</pluginId>
                <pluginArtifact>io.grpc:protoc-gen-grpc-java:1.68.1:exe:${os.detected.classifier}</pluginArtifact>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <goal>compile</goal>
                        <goal>compile-custom</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

### Remote Debugging

Enable remote debugging by adding to your run configuration:

```bash
JAVA_TOOL_OPTIONS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
```

Then connect your IDE debugger to `localhost:5005`

---

## 🧪 Testing

### Run Unit Tests

```bash
# All services
mvn test

# Specific service
cd auth-service && mvn test
```

### Run Integration Tests

```bash
mvn verify
```

### Test Coverage

```bash
mvn jacoco:report
```

View coverage report at: `target/site/jacoco/index.html`

---

## 🚢 Deployment

### Docker Compose Deployment

```bash
# Production build
docker-compose -f docker-compose.prod.yml up -d

# Scale services
docker-compose up -d --scale patient-service=3
```

### Kubernetes Deployment

```bash
# Apply configurations
kubectl apply -f k8s/

# Check deployments
kubectl get pods
kubectl get services

# View logs
kubectl logs -f deployment/patient-service
```

---

## 📊 Monitoring

### Health Checks

All services expose Spring Boot Actuator endpoints:

```bash
# Health
curl http://localhost:8080/actuator/health

# Metrics
curl http://localhost:8080/actuator/metrics

# Info
curl http://localhost:8080/actuator/info
```

### Logging

Logs are available via Docker:

```bash
# View all logs
docker-compose logs

# Follow specific service
docker-compose logs -f patient-service

# Last 100 lines
docker-compose logs --tail=100
```


---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---


## 🗺️ Roadmap

- [ ] Add Eureka Service Discovery
- [ ] Implement API Gateway with Spring Cloud Gateway
- [ ] Add Circuit Breaker with Resilience4j
- [ ] Implement Distributed Tracing with Zipkin
- [ ] Add Centralized Configuration with Spring Cloud Config
- [ ] Implement Rate Limiting
- [ ] Add GraphQL API
- [ ] Kubernetes Deployment Manifests
- [ ] Add Monitoring with Prometheus & Grafana
- [ ] Implement ELK Stack for logging

---

<div align="center">


[⬆ Back to Top](#-java-spring-microservices-architecture)

</div>
