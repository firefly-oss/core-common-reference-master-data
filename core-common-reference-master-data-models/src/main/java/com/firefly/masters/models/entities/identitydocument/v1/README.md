# Identity Document Catalog System

This document provides an overview of the Identity Document Catalog System, which manages various types of identity documents used for identification.

## Overview

The Identity Document Catalog System provides a flexible way to manage and use identity documents for various purposes:

1. **Identity Document Categories**: Categorizes identity documents (Personal, Business, Government, etc.)
2. **Identity Documents**: Defines the identity documents (Passport, National ID, Driver's License, etc.)
3. **Identity Document Localizations**: Provides translations of identity document information

The system supports:
- Document type categorization
- Country-specific document types
- Validation rules for document formats
- Localization for multiple languages
- Full CRUD operations via REST API

## Components

### Identity Document Category Catalog

The Identity Document Category Catalog defines the categories of identity documents. Each category has:

- A unique code
- A name
- A description

### Identity Document Catalog

The Identity Document Catalog defines the identity documents that can be used for identification. Each document has:

- A unique code
- A name
- A reference to a category
- A country association (for country-specific document types)
- A description
- Optional validation regex for format validation
- Optional format description

### Identity Document Localization

The Identity Document Localization component provides translations of identity document information for different languages/locales. Each localization has:

- A reference to an identity document
- A reference to a language/locale
- A localized document name
- A localized description
- A localized format description

## Usage Examples

### Creating an Identity Document Category

```json
POST /api/v1/identity-document-categories
{
  "categoryCode": "GOVERNMENT",
  "categoryName": "Government",
  "description": "Government-issued identification documents",
  "status": "ACTIVE"
}
```

Example response:
```json
{
  "categoryId": "550e8400-e29b-41d4-a716-446655440000",
  "categoryCode": "GOVERNMENT",
  "categoryName": "Government",
  "description": "Government-issued identification documents",
  "status": "ACTIVE",
  "dateCreated": "2023-06-15T11:30:00",
  "dateUpdated": "2023-06-15T11:30:00"
}
```

### Creating an Identity Document

```json
POST /api/v1/identity-documents
{
  "documentCode": "PASSPORT",
  "documentName": "Passport",
  "categoryId": "550e8400-e29b-41d4-a716-446655440000",
  "countryId": "550e8400-e29b-41d4-a716-446655440001",
  "description": "International passport for travel and identification",
  "validationRegex": "^[A-Z0-9]{9}$",
  "formatDescription": "9 characters, alphanumeric",
  "status": "ACTIVE"
}
```

Example response:
```json
{
  "documentId": "550e8400-e29b-41d4-a716-446655440002",
  "documentCode": "PASSPORT",
  "documentName": "Passport",
  "categoryId": "550e8400-e29b-41d4-a716-446655440000",
  "category": {
    "categoryId": "550e8400-e29b-41d4-a716-446655440000",
    "categoryCode": "GOVERNMENT",
    "categoryName": "Government",
    "description": "Government-issued identification documents",
    "status": "ACTIVE",
    "dateCreated": "2023-06-15T11:30:00",
    "dateUpdated": "2023-06-15T11:30:00"
  },
  "countryId": "550e8400-e29b-41d4-a716-446655440001",
  "description": "International passport for travel and identification",
  "validationRegex": "^[A-Z0-9]{9}$",
  "formatDescription": "9 characters, alphanumeric",
  "status": "ACTIVE",
  "dateCreated": "2023-06-15T12:45:00",
  "dateUpdated": "2023-06-15T12:45:00"
}
```

### Creating an Identity Document Localization

```json
POST /api/v1/identity-document-localizations
{
  "documentId": "550e8400-e29b-41d4-a716-446655440002",
  "localeId": "550e8400-e29b-41d4-a716-446655440003",
  "documentName": "Pasaporte",
  "description": "Pasaporte internacional para viajes e identificación",
  "formatDescription": "9 caracteres, alfanuméricos",
  "status": "ACTIVE"
}
```

Example response:
```json
{
  "localizationId": "550e8400-e29b-41d4-a716-446655440004",
  "documentId": "550e8400-e29b-41d4-a716-446655440002",
  "localeId": "550e8400-e29b-41d4-a716-446655440003",
  "documentName": "Pasaporte",
  "description": "Pasaporte internacional para viajes e identificación",
  "formatDescription": "9 caracteres, alfanuméricos",
  "status": "ACTIVE",
  "dateCreated": "2023-06-15T13:00:00",
  "dateUpdated": "2023-06-15T13:00:00"
}
```

### Listing Identity Documents by Category

```
GET /api/v1/identity-documents/category/550e8400-e29b-41d4-a716-446655440000?page=0&size=10
```

Example response:
```json
{
  "content": [
    {
      "documentId": "550e8400-e29b-41d4-a716-446655440002",
      "documentCode": "PASSPORT",
      "documentName": "Passport",
      "categoryId": "550e8400-e29b-41d4-a716-446655440000",
      "category": {
        "categoryId": "550e8400-e29b-41d4-a716-446655440000",
        "categoryCode": "GOVERNMENT",
        "categoryName": "Government",
        "description": "Government-issued identification documents",
        "status": "ACTIVE",
        "dateCreated": "2023-06-15T11:30:00",
        "dateUpdated": "2023-06-15T11:30:00"
      },
      "countryId": "550e8400-e29b-41d4-a716-446655440001",
      "description": "International passport for travel and identification",
      "validationRegex": "^[A-Z0-9]{9}$",
      "formatDescription": "9 characters, alphanumeric",
      "status": "ACTIVE",
      "dateCreated": "2023-06-15T11:35:00",
      "dateUpdated": "2023-06-15T11:35:00"
    },
    {
      "documentId": "550e8400-e29b-41d4-a716-446655440005",
      "documentCode": "NATIONAL_ID",
      "documentName": "National ID Card",
      "categoryId": "550e8400-e29b-41d4-a716-446655440000",
      "category": {
        "categoryId": "550e8400-e29b-41d4-a716-446655440000",
        "categoryCode": "GOVERNMENT",
        "categoryName": "Government",
        "description": "Government-issued identification documents",
        "status": "ACTIVE",
        "dateCreated": "2023-06-15T11:30:00",
        "dateUpdated": "2023-06-15T11:30:00"
      },
      "countryId": null,
      "description": "Generic national identification card",
      "status": "ACTIVE",
      "dateCreated": "2023-06-15T11:40:00",
      "dateUpdated": "2023-06-15T11:40:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 2,
  "totalPages": 1,
  "last": true,
  "size": 10,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "numberOfElements": 2,
  "first": true,
  "empty": false
}
```

### Getting Localizations for a Document

```
GET /api/v1/identity-document-localizations/document/550e8400-e29b-41d4-a716-446655440002?page=0&size=10
```

Example response:
```json
{
  "content": [
    {
      "localizationId": 1,
      "documentId": 1,
      "localeId": 2,
      "documentName": "Pasaporte",
      "description": "Pasaporte internacional para viajes e identificación",
      "formatDescription": "9 caracteres, alfanuméricos",
      "status": "ACTIVE",
      "dateCreated": "2023-06-15T13:00:00",
      "dateUpdated": "2023-06-15T13:00:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "size": 10,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "numberOfElements": 1,
  "first": true,
  "empty": false
}
```

### Getting a Specific Localization

```
GET /api/v1/identity-document-localizations/document/550e8400-e29b-41d4-a716-446655440002/locale/550e8400-e29b-41d4-a716-446655440003
```

Example response:
```json
{
  "localizationId": "550e8400-e29b-41d4-a716-446655440004",
  "documentId": "550e8400-e29b-41d4-a716-446655440002",
  "localeId": "550e8400-e29b-41d4-a716-446655440003",
  "documentName": "Pasaporte",
  "description": "Pasaporte internacional para viajes e identificación",
  "formatDescription": "9 caracteres, alfanuméricos",
  "status": "ACTIVE",
  "dateCreated": "2023-06-15T13:00:00",
  "dateUpdated": "2023-06-15T13:00:00"
}
```

### Updating a Localization

```json
PUT /api/v1/identity-document-localizations/550e8400-e29b-41d4-a716-446655440004
{
  "documentId": "550e8400-e29b-41d4-a716-446655440002",
  "localeId": "550e8400-e29b-41d4-a716-446655440003",
  "documentName": "Pasaporte",
  "description": "Pasaporte internacional para viajes e identificación personal",
  "formatDescription": "9 caracteres, alfanuméricos",
  "status": "ACTIVE"
}
```

Example response:
```json
{
  "localizationId": "550e8400-e29b-41d4-a716-446655440004",
  "documentId": "550e8400-e29b-41d4-a716-446655440002",
  "localeId": "550e8400-e29b-41d4-a716-446655440003",
  "documentName": "Pasaporte",
  "description": "Pasaporte internacional para viajes e identificación personal",
  "formatDescription": "9 caracteres, alfanuméricos",
  "status": "ACTIVE",
  "dateCreated": "2023-06-15T13:00:00",
  "dateUpdated": "2023-06-15T13:05:00"
}
```

### Deleting an Identity Document

```
DELETE /api/v1/identity-documents/550e8400-e29b-41d4-a716-446655440002
```

Response: HTTP 204 No Content

### Deleting a Localization

```
DELETE /api/v1/identity-document-localizations/550e8400-e29b-41d4-a716-446655440004
```

Response: HTTP 204 No Content

### Deleting All Localizations for a Document

```
DELETE /api/v1/identity-document-localizations/document/550e8400-e29b-41d4-a716-446655440002
```

Response: HTTP 204 No Content

## Integration with Other Microservices

Other microservices can integrate with the Identity Document Catalog through REST API calls. For example, to validate a document type in another service:

```java
@Service
public class DocumentValidationService {
    
    private final WebClient webClient;
    
    public DocumentValidationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://master-data-service").build();
    }
    
    public Mono<Boolean> validateDocumentFormat(String documentCode, String documentNumber) {
        return webClient.get()
                .uri("/api/v1/identity-documents/code/{code}", documentCode)
                .retrieve()
                .bodyToMono(IdentityDocumentDTO.class)
                .flatMap(document -> {
                    if (document.getValidationRegex() == null || document.getValidationRegex().isEmpty()) {
                        return Mono.just(true);
                    }
                    
                    Pattern pattern = Pattern.compile(document.getValidationRegex());
                    Matcher matcher = pattern.matcher(documentNumber);
                    return Mono.just(matcher.matches());
                })
                .onErrorResume(e -> Mono.just(false));
    }
}
```

To get a localized document name in a specific language:

```java
@Service
public class DocumentLocalizationService {
    
    private final WebClient webClient;
    
    public DocumentLocalizationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://master-data-service").build();
    }
    
    public Mono<String> getLocalizedDocumentName(String documentCode, UUID localeId) {
        return webClient.get()
                .uri("/api/v1/identity-documents/code/{code}", documentCode)
                .retrieve()
                .bodyToMono(IdentityDocumentDTO.class)
                .flatMap(document -> webClient.get()
                        .uri("/api/v1/identity-document-localizations/document/{documentId}/locale/{localeId}",
                             document.getDocumentId(), localeId)
                        .retrieve()
                        .bodyToMono(IdentityDocumentLocalizationDTO.class)
                        .map(IdentityDocumentLocalizationDTO::getDocumentName)
                        .onErrorReturn(document.getDocumentName()) // Fallback to default name if localization not found
                );
    }
}
```
