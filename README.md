# Spring Boot Microservices 

This project demonstrates building a microservices architecture using Java, Spring Boot, gRPC, Kafka, PostgreSQL, and Docker.  
It covers key concepts such as service communication, authentication, event-driven architecture, and database integration.

## Included Services
- **Patient Service** – Handles patient data and integrates with Billing and Notification services.
- **Billing Service** – Provides gRPC-based billing functionality.
- **Notification Service** – Publishes Kafka events for notifications.
- **Auth Service** – Manages user authentication and roles using JWT.

## Key Features
- Microservice communication via gRPC
- Event-driven messaging with Kafka
- PostgreSQL for persistent storage
- Docker Compose for easy multi-container setup
- JWT-based security
- Protobuf integration for message serialization

## Environment Setup
Uses `.env` for service configuration, including database URLs, Kafka settings, and gRPC ports.

## Build & Run
Standard Maven build with Protobuf compilation and Spring Boot application startup.

## Why It Matters
This project demonstrates practical use of microservice patterns, security, event-driven design, and containerization in a real-world backend system.

---

📚 Original Course by Chris Blakely | [YouTube Tutorial](https://www.youtube.com/channel/UCeWbV8yycXHe7o6x0hzL-kg)
