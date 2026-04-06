# menu-api

Spring Boot API that loads a restaurant menu from an XML file (`menu.xmlPath`) and exposes endpoints to browse categories, products, and modifiers.

## Prerequisites

- Java 17
- Maven (or use the included Maven Wrapper if present)

## Configuration

The application reads the menu XML path from `src/main/resources/application.yml`:

- `menu.xmlPath`: path to the menu XML file
  - Can be an absolute path, or a path relative to the project working directory.

Example (current default):

```yaml
menu:
  xmlPath: 24405.xml
```

## Install / Build

From the repo root:

```bash
mvn clean install
```

## Run

### Run with Maven

```bash
mvn spring-boot:run
```

### Run the packaged jar

```bash
mvn clean package
java -jar target/menu-api-0.0.1-SNAPSHOT.jar
```

The server runs on:

- `http://localhost:8080`

## API Documentation (Swagger)

- Swagger UI:
  - `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON:
  - `http://localhost:8080/v3/api-docs`

## API Endpoints

Base paths:

- Categories: `/api/v1/categories`
- Products: `/api/v1/products`

### Categories

- `GET /api/v1/categories`
  - List categories (paged)
- `GET /api/v1/categories/id-names`
  - List category id/name pairs
- `GET /api/v1/categories/{categoryId}?includeProducts=true|false`
  - Get a category
  - `includeProducts=false` (default) returns the category without the potentially large products list
- `GET /api/v1/categories/{categoryId}/products-kv`
  - Returns all products in the category as **key/value pairs** (`List<Map<String,Object>>`)
  - Returns `404` if the category does not exist

### Products

- `GET /api/v1/products`
  - List products with optional filters:
    - `categoryId`
    - `minCalories`
    - `maxCalories`
    - `q`
    - `ingredient` (single ingredient substring match)
    - `includeDisabled` (default `false`)

- `GET /api/v1/products/{productId}`
  - Get product by id
  - Returns `404` if not found

- `GET /api/v1/products/by-ingredients`
  - Filter products by **one or more ingredients** (case-insensitive match against `product.description`)
  - Query params:
    - `ingredients` (required)
      - repeated: `?ingredients=tomato&ingredients=cheese`
      - or comma-separated: `?ingredients=tomato,cheese`
    - `matchMode` = `any` (default) or `all`
    - optional: `categoryId`, `minCalories`, `maxCalories`, `q`, `includeDisabled`

- `GET /api/v1/products/by-calories`
  - Dedicated calories endpoint
  - Query params:
    - `minCalories` (optional)
    - `maxCalories` (optional)
    - optional: `categoryId`, `includeDisabled`
  - Returns `400` if `minCalories > maxCalories`

- `GET /api/v1/products/by-price`
  - Dedicated price endpoint (filters by `ProductXml.cost` parsed as `BigDecimal`)
  - Query params:
    - `minPrice` (optional)
    - `maxPrice` (optional)
    - optional: `categoryId`, `includeDisabled`
  - Returns:
    - `400` if neither `minPrice` nor `maxPrice` is provided
    - `400` if `minPrice > maxPrice`

## Examples

```bash
# Products between 500 and 900 calories
curl "http://localhost:8080/api/v1/products/by-calories?minCalories=500&maxCalories=900"

# Products matching any of the listed ingredients
curl "http://localhost:8080/api/v1/products/by-ingredients?ingredients=chicken&ingredients=jalape%C3%B1o"

# Products between $1.00 and $5.00
curl "http://localhost:8080/api/v1/products/by-price?minPrice=1.00&maxPrice=5.00"

# Category products returned as key/value pairs
curl "http://localhost:8080/api/v1/categories/123/products-kv"
```
