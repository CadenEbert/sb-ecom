# SB-Ecom - Spring Boot E-Commerce Application

A RESTful API for an e-commerce platform built with Spring Boot, featuring category management, pagination, and comprehensive unit tests.

## Features

- **Category Management**: CRUD operations for product categories
- **Pagination & Filtering**: Support for paginated category retrieval with sorting
- **RESTful API**: Clean REST API design following best practices
- **Input Validation**: Spring Boot Validation framework integration
- **Unit Testing**: Comprehensive test coverage using JUnit 5 and Mockito
- **Database**: H2 in-memory database for development and testing

## Tech Stack

- **Java 21**
- **Spring Boot 3.2.1**
- **Spring Data JPA**
- **Spring MVC**
- **JUnit 5**
- **Mockito**
- **Maven**
- **H2 Database**
- **Lombok**
- **ModelMapper**

## Project Structure

```
sb-ecom/
├── src/
│   ├── main/
│   │   ├── java/com/ecommerce/project/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── entity/
│   │   │   ├── payload/
│   │   │   ├── repository/
│   │   │   ├── exceptions/
│   │   │   ├── config/
│   │   │   └── SbEcomApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/ecommerce/project/
│           └── controller/
├── pom.xml
└── README.md
```

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6.0 or higher

### Installation

1. Clone the repository:
```bash
git clone https://github.com/CadenEbert/sb-ecom.git
cd sb-ecom
```

2. Install dependencies and build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

- **Get All Categories**
  ```
  GET /api/public/categories
  ```
  Query Parameters: `pageNumber`, `pageSize`, `sortBy`, `sortOrder`
  
  Response:
  ```json
  {
    "content": [
      {
        "categoryId": 1,
        "categoryName": "Electronics"
      }
    ],
    "pageNumber": 0,
    "pageSize": 10,
    "totalElements": 1,
    "totalPages": 1,
    "lastPage": true
  }
  ```

- **Create Category**
  ```
  POST /api/public/categories
  ```
  Request Body:
  ```json
  {
    "categoryName": "Electronics"
  }
  ```

- **Update Category**
  ```
  PUT /api/admin/categories/{categoryId}
  ```
  Request Body:
  ```json
  {
    "categoryName": "Updated Category Name"
  }
  ```


- **Delete Category**
  ```
  DELETE /api/admin/categories/{categoryId}
  ```

  ### Product Endpoints

- **Get All Products**
  ```
  GET /api/public/products
  ```
  Query Parameters: `pageNumber`, `pageSize`, `sortBy`, `sortOrder`

- **Get Product by ID**
  ```
  GET /api/public/products/{productId}
  ```

- **Get Products by Category**
  ```
  GET /api/public/categories/{categoryId}/products
  ```

- **Create Product**
  ```
  POST /api/public/products
  ```
  Request Body:
  ```json
  {
    "productName": "Laptop",
    "description": "High-performance laptop",
    "price": 999.99,
    "categoryId": 1
  }
  ```

- **Update Product**
  ```
  PUT /api/admin/products/{productId}
  ```
  Request Body:
  ```json
  {
    "productName": "Updated Product",
    "description": "Updated description",
    "price": 1099.99
  }
  ```

- **Delete Product**
  ```
  DELETE /api/admin/products/{productId}
  ```


## Testing

Run the test suite:
```bash
mvn test
```

### Test Coverage

- `testGetCategories()` - Tests retrieval of all categories
- `testCreateCategory()` - Tests category creation
- `testUpdateCategory()` - Tests category updates
- `testDeleteCategory()` - Tests category deletion


## Dependencies

Key dependencies included:
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- H2 Database
- Lombok
- ModelMapper
- JUnit 5

