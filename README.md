# Firefly Platform - Reference Master Data Microservice

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Key Features](#key-features)
- [Project Structure](#project-structure)
- [Master Data Catalogs](#master-data-catalogs)
  - [Geographic Data](#geographic-data)
  - [Financial Data](#financial-data)
  - [Identity and Relationships](#identity-and-relationships)
  - [Communication and Notifications](#communication-and-notifications)
  - [Transaction Categories](#transaction-categories)
  - [Miscellaneous](#miscellaneous)
- [Technical Stack](#technical-stack)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Environment Setup](#environment-setup)
  - [Building the Application](#building-the-application)
  - [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Deployment](#deployment)
  - [Docker Deployment](#docker-deployment)
  - [Kubernetes Deployment](#kubernetes-deployment)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Overview

The Reference Master Data Microservice is a core component of the Firefly Platform, providing centralized management of reference data used across the platform. This microservice serves as the single source of truth for various types of master data, ensuring consistency and standardization across all platform services.

Built with a reactive architecture using Spring WebFlux and R2DBC, this microservice offers high-performance, non-blocking APIs for managing and retrieving reference data. The catalog-based design allows for flexible and dynamic configuration of reference data, making it adaptable to changing business requirements.

## Architecture

The Reference Master Data Microservice follows a reactive microservice architecture pattern:

- **Reactive Programming**: Built on Spring WebFlux and Project Reactor for non-blocking I/O operations
- **Database Connectivity**: Uses R2DBC for reactive database access
- **RESTful APIs**: Exposes reactive REST endpoints for CRUD operations on master data
- **Modular Design**: Organized into separate modules for clear separation of concerns
- **Catalog-Based Approach**: Uses catalog entities for flexible and configurable reference data

## Key Features

- **Comprehensive Master Data Management**: Centralized repository for all reference data used across the platform
- **Reactive Architecture**: Non-blocking APIs built with Spring WebFlux and R2DBC for high throughput and scalability
- **Catalog-Based Design**: Flexible catalog entities for dynamic configuration without code changes
- **Hierarchical Data Structures**: Support for parent-child relationships in catalogs like transaction categories
- **Internationalization Support**: Extensive localization capabilities for global deployments
- **Pagination and Filtering**: Efficient data retrieval with pagination, sorting, and filtering options
- **OpenAPI Documentation**: Comprehensive API documentation with Swagger UI
- **Database Migration**: Automated schema management with Flyway
- **Monitoring and Health Checks**: Production-ready with Spring Actuator endpoints
- **Validation Rules**: Support for validation rules and regex patterns for data integrity
- **Versioning**: API versioning for backward compatibility

## Project Structure

The microservice is organized into the following modules:

- **common-platform-reference-master-data-core**: Core business logic and service implementations
  - Contains service implementations for all catalog entities
  - Implements business rules and validation logic
  - Handles data transformation between entities and DTOs

- **common-platform-reference-master-data-models**: Data models, entities, and repositories
  - Defines database entities and their relationships
  - Contains repository interfaces for data access
  - Includes database migration scripts

- **common-platform-reference-master-data-interfaces**: DTOs and service interfaces
  - Defines Data Transfer Objects (DTOs) for API requests and responses
  - Contains service interfaces that define the contract for service implementations
  - Includes request/response models for API endpoints

- **common-platform-reference-master-data-web**: REST controllers and web configuration
  - Implements REST controllers for all API endpoints
  - Contains web-specific configurations
  - Handles API request validation and error responses

## Master Data Catalogs

The microservice manages the following types of master data:

### Geographic Data

- **Countries**: ISO codes, names, regions, and status
  - Includes ISO 3166-1 country codes
  - Supports country status (active/inactive)
  - Contains region and subregion information

- **Administrative Divisions**: States, provinces, and other administrative regions
  - Hierarchical structure for administrative divisions
  - Linked to countries for geographic organization
  - Includes codes and names for each division

- **Branches**: Physical locations and branches
  - Stores information about physical branch locations
  - Includes address, contact information, and operating hours
  - Supports branch status and type classification

### Financial Data

- **Currencies**: ISO codes, names, symbols, and decimal precision
  - Includes ISO 4217 currency codes
  - Contains currency symbols and formatting rules
  - Specifies decimal precision for monetary calculations

- **Bank Institution Codes**: Bank identification codes and routing numbers
  - Supports various code types (SWIFT/BIC, routing numbers, etc.)
  - Includes validation rules for different code formats
  - Links institutions to countries and regions

- **Holidays**: Financial and business holidays by country and region
  - Defines holidays that affect business operations
  - Supports recurring and one-time holidays
  - Allows country and region-specific holiday definitions

### Identity and Relationships

- **Identity Document Catalog**: Types of identity documents with validation rules
  - Defines various identity document types (passport, ID card, etc.)
  - Includes validation rules for document formats
  - Supports country-specific document types

- **Identity Document Categories**: Categorization of identity documents
  - Groups identity documents into categories (personal, business, government, etc.)
  - Provides a hierarchical organization of document types
  - Allows for flexible document type classification

- **Relationship Types**: Defines relationship types between entities
  - Specifies various relationship types (family, business, etc.)
  - Supports directional relationships
  - Includes relationship attributes and constraints

- **Titles**: Personal titles (Mr., Mrs., Dr., etc.)
  - Defines personal and professional titles
  - Supports localization for different languages
  - Includes gender-specific title information

### Communication and Notifications

- **Notification Message Catalog**: Templates for notifications across different channels
  - Defines message templates for various events
  - Supports variable substitution in templates
  - Includes default subjects and message bodies

- **Message Type Catalog**: Types of messages (Email, SMS, Push, In-App)
  - Categorizes messages by delivery channel
  - Defines channel-specific formatting rules
  - Supports status tracking for message types

- **Document Templates**: Templates for generating documents
  - Provides templates for various document types
  - Supports variable substitution and conditional sections
  - Includes versioning for template management

### Transaction Categories

- **Transaction Category Catalog**: Hierarchical categories for financial transactions
  - Supports parent-child relationships between categories
  - Provides a multi-level categorization system
  - Includes category codes, names, and descriptions
  - Allows for flexible transaction classification

### Miscellaneous

- **Legal Forms**: Types of legal entities and business structures
  - Defines various legal entity types
  - Includes country-specific legal forms
  - Contains attributes and requirements for each form

- **Language Locales**: Supported languages and locales
  - Defines language codes and locale information
  - Supports regional language variants
  - Includes display names and formatting rules

- **Consent Catalog**: Types of consents and their descriptions
  - Defines various consent types for regulatory compliance
  - Includes consent text templates
  - Supports versioning for consent management

- **Activity Codes**: Business activity classification codes
  - Provides standardized activity classification
  - Supports hierarchical activity categorization
  - Includes industry-standard classification codes

- **Lookup Domains and Items**: Generic lookup values for various domains
  - Flexible system for defining domain-specific lookup values
  - Supports hierarchical organization of lookup items
  - Allows for dynamic configuration of reference data

## Technical Stack

- **Java 25**: Latest LTS version with modern language features
- **Spring Boot 3.x**: Application framework with latest features
- **Spring WebFlux**: Reactive web framework for non-blocking APIs
- **R2DBC**: Reactive database connectivity for non-blocking database access
- **PostgreSQL**: Primary database for data storage
- **Flyway**: Database migration tool for schema management
- **SpringDoc OpenAPI**: API documentation with Swagger UI
- **JUnit 5 & Mockito**: Testing framework for unit and integration tests
- **Project Reactor**: Reactive programming library for asynchronous operations
- **Lombok**: Reduces boilerplate code in Java classes
- **MapStruct**: Type-safe bean mapping for DTO conversions
- **Docker**: Containerization for deployment
- **Micrometer**: Application metrics collection
- **Spring Actuator**: Production-ready features for monitoring and management

## Getting Started

### Prerequisites

- Java 25 or higher
- PostgreSQL 13 or higher
- Maven 3.8 or higher
- Docker (optional, for containerized deployment)

### Environment Setup

The following environment variables need to be set:

```properties
# Database Configuration
DB_HOST=localhost
DB_PORT=5432
DB_NAME=master_data
DB_USERNAME=postgres
DB_PASSWORD=postgres
DB_SSL_MODE=disable

# Application Configuration
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=dev
```

Alternatively, you can create an `.env` file in the project root with these variables for Docker deployment.

### Building the Application

```bash
# Clone the repository
git clone https://github.com/firefly-oss/common-platform-reference-master-data.git
cd common-platform-reference-master-data

# Build the project
mvn clean install
```

### Running the Application

```bash
# Run with Maven
mvn spring-boot:run -pl common-platform-reference-master-data-web

# Or run with Java
java -jar common-platform-reference-master-data-web/target/common-platform-reference-master-data-web-1.0.0-SNAPSHOT.jar
```

## API Documentation

Once the application is running, you can access the Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

The OpenAPI specification is available at:

```
http://localhost:8080/v3/api-docs
```

## Testing

The microservice includes a comprehensive test suite covering all service implementations. Tests are organized to match the package structure of the main application code.

```bash
# Run all tests
mvn test

# Run tests for a specific module
mvn test -pl common-platform-reference-master-data-core

# Run a specific test class
mvn test -Dtest=CountryServiceImplTest
```

## Deployment

### Docker Deployment

The application is containerized and can be deployed using Docker:

```bash
# Build the Docker image
docker build -t firefly-master-data .

# Run the container
docker run -p 8080:8080 --env-file .env firefly-master-data
```

### Kubernetes Deployment

For Kubernetes deployment, sample manifests are provided in the `k8s` directory:

```bash
# Apply Kubernetes manifests
kubectl apply -f k8s/
```

## Contributing

We welcome contributions to the Reference Master Data Microservice. Please follow these steps to contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

Please ensure your code follows the project's coding standards and includes appropriate tests.

## License

This project is under the Apache 2.0 License. See the [LICENSE](LICENSE) file for details.

