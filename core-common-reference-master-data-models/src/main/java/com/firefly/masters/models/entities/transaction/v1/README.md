# Transaction Category Catalog

This package contains the implementation of a hierarchical transaction category catalog for categorizing banking transactions.

## Overview

The Transaction Category Catalog provides a flexible way to categorize banking transactions with a hierarchical structure. It supports:

- Parent-child relationships between categories
- Multiple levels of categorization (e.g., root categories, subcategories, and further nested categories)
- SVG icons for visual representation of categories
- Localization support for multiple languages
- Full CRUD operations via REST API
- Pagination for efficient data retrieval

## Components

### Transaction Category Catalog

The Transaction Category Catalog defines the categories that can be used for transaction categorization. Each category has:

- A unique code
- A name
- A description
- An optional reference to a parent category (for hierarchical structure)
- An optional SVG icon for visual representation
- A status (ACTIVE, INACTIVE, etc.)

### Transaction Category Localization

The Transaction Category Localization component provides translations of transaction category information for different languages/locales. Each localization has:

- A reference to a transaction category
- A reference to a language/locale
- A localized category name
- A localized description

## Usage Examples

### Creating a Root Transaction Category

```json
POST /api/v1/transaction-categories
{
  "categoryCode": "INCOME",
  "categoryName": "Income",
  "description": "Money received from various sources",
  "svgIcon": "<svg>...</svg>",
  "status": "ACTIVE"
}
```

### Creating a Child Transaction Category

```json
POST /api/v1/transaction-categories
{
  "categoryCode": "SALARY",
  "categoryName": "Salary",
  "description": "Regular income from employment",
  "parentCategoryId": 1,
  "status": "ACTIVE"
}
```

### Retrieving Root Categories

```
GET /api/v1/transaction-categories/root
```

### Retrieving Child Categories

```
GET /api/v1/transaction-categories/parent/{parentCategoryId}
```

### Retrieving a Specific Category

```
GET /api/v1/transaction-categories/{categoryId}
```

or

```
GET /api/v1/transaction-categories/code/{categoryCode}
```

### Creating a Transaction Category Localization

```json
POST /api/v1/transaction-category-localizations
{
  "categoryId": 1,
  "localeId": 2,
  "categoryName": "Ingresos",
  "description": "Dinero recibido de varias fuentes",
  "status": "ACTIVE"
}
```

### Retrieving Localizations for a Category

```
GET /api/v1/transaction-category-localizations/category/{categoryId}
```

### Retrieving a Specific Localization

```
GET /api/v1/transaction-category-localizations/category/{categoryId}/locale/{localeId}
```

## Hierarchical Structure Example

The transaction category catalog supports a hierarchical structure, allowing for multiple levels of categorization:

- Income (Root Category)
  - Salary (Child Category)
  - Interest (Child Category)
  - Dividend (Child Category)
  - Rental (Child Category)
- Expense (Root Category)
  - Housing (Child Category)
    - Rent (Grandchild Category)
    - Mortgage Payment (Grandchild Category)
    - Property Tax (Grandchild Category)
  - Food (Child Category)
    - Groceries (Grandchild Category)
    - Restaurants (Grandchild Category)
  - Transportation (Child Category)
  - Utilities (Child Category)

This hierarchical structure allows for more detailed categorization of transactions, which can be useful for financial analysis and reporting.
