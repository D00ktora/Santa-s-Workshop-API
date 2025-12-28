# 🎅 Santa’s Workshop API

## 📌 About the Project

**Santa’s Workshop API** is a Spring Boot REST application created as an **exam task for Silva Academy**.

The application helps Santa manage his workshop by:

* Creating gifts from children’s letters
* Assigning gifts to elves for production
* Wrapping and tracking gifts
* Creating, managing, and tracking deliveries

The system supports full CRUD operations, validation, filtering, searching, pagination, centralized error handling, and database initialization.

---

## ⚙️ Initialization & Configuration

Before running the application, configure the database connection in:

`src/main/resources/application.properties`

### Required changes

* **Line 3** – Database name (default: `santa_database`)
* **Line 4** – SQL username
* **Line 5** – SQL password
* **Line 8** – Application port (default: `8080`)

⚠️ If port `8080` is already in use, the application will not start.

💡 For production or cloud deployment, use **environment variables** instead of hardcoded credentials.

---

## 🧱 Project Architecture Overview

The project follows a layered architecture:

* **Controllers** – Handle HTTP requests and responses
* **Services** – Business logic
* **Repositories** – Database access
* **DTOs** – Data transfer between layers
* **Entities** – Database models
* **Configuration** – Error handling, mapping, initialization
* **JSON Data** – Database population on first start

---

## 📁 Detailed Project Structure

### 🔴 Error Handling

**src/main/java/Santas_Workshop_API/config/errorHandling/exceptions**
Contains custom exception classes used to extend standard exceptions and represent domain-specific errors. These are passed to the global controller advice.

**src/main/java/Santas_Workshop_API/config/errorHandling**
Contains `GlobalExceptionHandler`, which handles all custom and validation exceptions and returns consistent JSON error responses.

---

### 🔵 Configuration

**src/main/java/Santas_Workshop_API/config**

Contains:

* Three MapStruct mapper interfaces
* `DataInitializer`

**DataInitializer**

* Loads initial data from JSON files
* Executes only if the database is empty
* Prevents duplicate data insertion

**Mappers**

* Convert DTOs to entities and vice versa
* Reduce boilerplate code
* Keep services clean and readable

---

## 🌐 Controllers & Endpoints

### 🚚 Delivery Controller

**POST `/api/deliveries`**
Creates a delivery.

Input JSON:

```
{
  "address": "string",
  "recipientName": "string",
  "deliveryStatus": "string",
  "gifts": [1, 2, 3]
}
```

Validates input and throws `BadRequestException` on validation errors.

**GET `/api/deliveries`**
Returns all created deliveries that are not delivered or failed.
Optional query parameters: `deliveryStatus`, `recipientName`.

**PATCH `/api/deliveries/{id}/status`**
Updates delivery status.
Allowed transitions:
`LOADED → IN_TRANSIT → DELIVERED / FAILED`
Invalid transitions result in an error.

---

### 🧝 Elf Controller

**POST `/api/elves`**
Creates an elf.

Input JSON:

```
{
  "name": "string",
  "skillLevel": "JUNIOR | MID | SENIOR"
}
```

**GET `/api/elves`**
Returns all elves.

**GET `/api/elves/{id}`**
Returns a single elf by ID or `404` if not found.

**DELETE `/api/elves/{id}`**
Deletes an elf. Returns `201` on success.

**POST `/api/elves/{elfId}/assign/{giftId}`**
Assigns a gift to an elf.
A gift can be reassigned from one elf to another.
Returns `404` if elf or gift does not exist.

---

### 🎁 Gift Controller

**POST `/api/gifts`**
Creates a gift.

Input JSON:

```
{
  "name": "string",
  "category": "TOY | BOOK | GADGET | CLOTHES | OTHER",
  "targetAge": 10,
  "isWrapped": "true | false"
}
```

**GET `/api/gifts`**
Returns paginated and filtered gifts.
Optional parameters: `status`, `category`, `wrapped`, `page`, `pageSize`, `sort`.

**GET `/api/gifts/{id}`**
Returns a gift by ID or `404` if not found.

**PUT `/api/gifts/{id}`**
Fully updates a gift.

**PATCH `/api/gifts/{id}/wrap`**
Wraps the gift if it is not already wrapped.

**DELETE `/api/gifts/{id}`**
Deletes a gift.

**GET `/api/gifts/search`**
Searches gifts by partial name (case-insensitive).

---

### 🏠 Home Controller

**GET `/api`**
Returns general application information.

**GET `/api/stats`**
Returns statistics for gifts and deliveries.

---

## 📦 DTO Structure

* `entity/DTO/deliveries` – Delivery DTOs
* `entity/DTO/elves` – Elf DTOs
* `entity/DTO/elves/customValidation` – Custom elf validations
* `entity/DTO/gifts` – Gift DTOs
* `entity/DTO/exceptions` – Error response DTO
* `entity/DTO/general` – General-purpose DTOs

---

## 🧬 Entities & Enums

* `entity` – All JPA entities
* `entity/enums` – Enum classes grouped by domain (gifts, deliveries, elves)

---

## 🗄 Repositories

Located in:
`src/main/java/Santas_Workshop_API/repository`

Includes repositories for:

* Gifts
* Elves
* Deliveries
* Delivered gifts
* Delivered or failed deliveries

---

## 🔧 Services

**Service implementations:**
`src/main/java/Santas_Workshop_API/service/impl`

**Service interfaces:**
`src/main/java/Santas_Workshop_API/service`

Services contain the core business logic.

---

## 📊 Database Initialization

Located in:
`src/main/resources/data`

Contains JSON files used to populate the database on the first application start.
Initialization runs only when the database is empty.

---

## ✅ Summary

Santa’s Workshop API is a complete REST application featuring:

* Clean layered architecture
* DTO-based communication
* Validation with groups
* Centralized exception handling
* Pagination, filtering, and searching
* Automatic database initialization

🎄 Happy Coding & Merry Christmas 🎄

This readme is created with AI, based on my input and explanations its just format it properly. :)


