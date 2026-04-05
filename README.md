# Finance Data Processing and Access Control Backend

## Project Overview
A backend system for a finance dashboard application built using Spring Boot and MySQL.
The system supports financial record management, user role management,
role-based access control, and dashboard summary APIs.

---

## Tech Stack
| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 21 | Programming Language |
| Spring Boot | 3.5 | Backend Framework |
| Spring Security | 6.5 | Authentication & Authorization |
| Spring Data JPA | 3.5 | Database ORM |
| MySQL | 8.0 | Relational Database |
| Hibernate | 6.6 | ORM Implementation |
| Lombok | 1.18 | Reduce Boilerplate Code |
| Maven | 3.9 | Build Tool |
| BCrypt | - | Password Encryption |

---

## Architecture Overview

This project follows a **layered architecture**, which separates responsibilities into different layers for better maintainability and scalability.

### Controller Layer
Handles incoming HTTP requests and sends responses to clients.  
Controllers expose REST APIs for users, financial records, and dashboard operations.

### Service Layer
Contains the **core business logic** of the application.  
It processes data, enforces business rules, and communicates between controllers and repositories.

### Repository Layer
Responsible for **database access** using Spring Data JPA.  
Repositories interact with the MySQL database through Hibernate ORM.

### Model Layer
Contains entity classes such as **User** and **FinancialRecord** that represent database tables.

### Security Layer
Spring Security is used to implement **HTTP Basic Authentication and Role-Based Access Control (RBAC)**.  
User passwords are encrypted using **BCrypt** before storing in the database.

---

## Prerequisites

Before running this project, ensure the following tools are installed:

- Java 21
- MySQL 8.0
- Maven 3.9+
- Postman (for API testing)
- IntelliJ IDEA or any Java IDE

---

## Setup Instructions

### Step 1 — Clone the Repository
```bash
git clone https://github.com/sweekrithishetty28/finance-backend.git
cd finance-backend
```

### Step 2 — Create MySQL Database
Open MySQL Workbench or MySQL CLI and run:
```sql
CREATE DATABASE finance_db;
```

### Step 3 — Configure Database
Open `src/main/resources/application.properties` and update:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/finance_db
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
```

### Step 4 — Run the Project
Using Maven:
```bash
mvn spring-boot:run
```
Or run `FinanceApplication.java` directly from IntelliJ IDEA!

### Step 5 — Server Starts At
```
http://localhost:8080
```

### Step 6 — Create First Admin User
Since the database is empty, the first user can be created without authentication:
```json
POST http://localhost:8080/users
{
    "name": "Admin User",
    "email": "admin@gmail.com",
    "password": "admin123",
    "role": "ADMIN",
    "status": "ACTIVE"
}
```

---

## Authentication
The application uses **HTTP Basic Authentication**.

For every request in Postman:
1. Go to **Authorization** tab
2. Select **Basic Auth**
3. Enter:
   - Username → email
   - Password → password

---

## User Roles and Permissions

| Action | VIEWER | ANALYST | ADMIN |
|--------|--------|---------|-------|
| View financial records | ✅ | ✅ | ✅ |
| View dashboard summary | ✅ | ✅ | ✅ |
| Create financial records | ❌ | ✅ | ✅ |
| Update financial records | ❌ | ✅ | ✅ |
| Delete financial records | ❌ | ❌ | ✅ |
| Manage users | ❌ | ❌ | ✅ |
| Change user roles | ❌ | ❌ | ✅ |

---

## API Endpoints

### Users API `/users`
| Method | Endpoint | Description | Role |
|--------|----------|-------------|------|
| POST | `/users` | Create user | Public |
| GET | `/users` | Get all users | ADMIN |
| GET | `/users/{id}` | Get user by ID | ADMIN |
| PUT | `/users/{id}` | Update user | ADMIN |
| DELETE | `/users/{id}` | Delete user | ADMIN |
| PUT | `/users/{id}/role` | Change user role | ADMIN |
| PUT | `/users/{id}/status` | Change user status | ADMIN |

### Financial Records API `/financial-records`
| Method | Endpoint | Description | Role |
|--------|----------|-------------|------|
| POST | `/financial-records` | Create record | ANALYST, ADMIN |
| GET | `/financial-records` | Get all records | ALL |
| GET | `/financial-records/{id}` | Get record by ID | ALL |
| PUT | `/financial-records/{id}` | Update record | ANALYST, ADMIN |
| DELETE | `/financial-records/{id}` | Soft delete record | ADMIN |
| GET | `/financial-records/category/{category}` | Filter by category | ALL |
| GET | `/financial-records/date/{date}` | Filter by date | ALL |
| GET | `/financial-records/type/{type}` | Filter by type | ALL |
| GET | `/financial-records/my-records` | Get my records | ALL |

### Dashboard API `/dashboard`
| Method | Endpoint | Description | Role |
|--------|----------|-------------|------|
| GET | `/dashboard/summary` | Complete summary | ALL |
| GET | `/dashboard/total-income` | Total income | ALL |
| GET | `/dashboard/total-expenses` | Total expenses | ALL |
| GET | `/dashboard/net-balance` | Net balance | ALL |
| GET | `/dashboard/category-totals` | Category wise totals | ALL |
| GET | `/dashboard/recent` | Recent 5 transactions | ALL |

---

## Sample API Requests

### Create User
```json
POST /users
{
    "name": "Rahul Sharma",
    "email": "rahul@gmail.com",
    "password": "rahul123",
    "role": "ADMIN",
    "status": "ACTIVE"
}
```

### Create Financial Record
```json
POST /financial-records
Authorization: Basic Auth

{
    "amount": 50000.00,
    "transactionType": "INCOME",
    "category": "Salary",
    "date": "2026-04-01",
    "description": "Monthly salary April",
    "createdBy": {
        "id": 1
    }
}
```

### Dashboard Summary Response
```json
{
    "totalIncome": 65000.0,
    "totalExpenses": 22500.0,
    "netBalance": 42500.0,
    "categoryTotals": {
        "Salary": 50000.0,
        "Freelance": 15000.0,
        "Food": 3000.0,
        "Travel": 2500.0,
        "Rent": 12000.0,
        "Shopping": 5000.0
    },
    "recentTransactions": []
}
```

---

## Database Schema

### Users Table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary Key auto increment |
| name | VARCHAR | User full name |
| email | VARCHAR | Unique email address |
| password | VARCHAR | BCrypt encrypted password |
| role | ENUM | VIEWER, ANALYST, ADMIN |
| status | ENUM | ACTIVE, INACTIVE |

### Financial Records Table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary Key auto increment |
| amount | DOUBLE | Transaction amount |
| transaction_type | ENUM | INCOME, EXPENSE |
| category | VARCHAR | Transaction category |
| date | DATE | Transaction date |
| description | VARCHAR | Transaction description |
| is_deleted | BOOLEAN | Soft delete flag |
| created_by_id | BIGINT | Foreign key to users table |

---

## Validation Rules

### User Validation
- Name cannot be empty
- Email must be valid format and unique
- Password cannot be empty
- Role must be VIEWER, ANALYST or ADMIN
- Status must be ACTIVE or INACTIVE

### Financial Record Validation
- Amount must be positive and cannot be null
- Transaction type cannot be null
- Category cannot be empty
- Date cannot be null

---

## Error Handling

| Status Code | Meaning |
|-------------|---------|
| 200 | Request successful |
| 201 | Resource created successfully |
| 400 | Invalid input data |
| 401 | Missing or invalid credentials |
| 403 | Insufficient permissions |
| 404 | Resource not found |
| 500 | Internal server error |

---

## Assumptions Made
1. The first user can be created without authentication to bootstrap the system
2. Soft delete is used for financial records to preserve transaction history
3. Users can be deactivated using status field instead of hard deleting them
4. Email must be unique for each user
5. All authenticated users can view dashboard summary
6. Financial records belong to the user who created them
7. Passwords are encrypted using BCrypt before storing

---

## Design Decisions and Tradeoffs

### HTTP Basic Authentication
- Basic authentication was chosen because it is simple and suitable for API testing
- In production systems, JWT authentication would be preferred for better security

### Soft Delete
- Financial records are not permanently deleted
- An `is_deleted` flag is used to maintain audit trail
- This is important in finance systems for regulatory compliance

### Enum Usage
- Enums are used for roles, status and transaction types to ensure data integrity
- Stored as STRING in database for readability

### Service Layer Business Logic
- All calculations such as dashboard summaries are handled in the service layer
- This keeps controllers thin and business logic testable

---

## Future Improvements
- Implement JWT Authentication
- Add pagination and sorting for record listing
- Add Swagger API documentation
- Add caching for dashboard APIs
- Add unit and integration tests
- Export reports as CSV or PDF

---

## API Documentation
Postman Collection:
```
https://documenter.getpostman.com/view/50004875/2sBXiqE8qr
```

---

## Project Structure
```
src/
└── main/
    ├── java/
    │   └── com/example/finance/FinanceApplication/
    │       ├── config/
    │       │   ├── SecurityConfig.java
    │       │   └── GlobalExceptionHandler.java
    │       ├── controller/
    │       │   ├── UserController.java
    │       │   ├── FinancialRecordController.java
    │       │   └── DashboardController.java
    │       ├── model/
    │       │   ├── User.java
    │       │   ├── FinancialRecords.java
    │       │   ├── Role.java
    │       │   ├── Status.java
    │       │   └── TransactionType.java
    │       ├── repository/
    │       │   ├── UserRepository.java
    │       │   └── FinancialRecordRepository.java
    │       ├── service/
    │       │   ├── UserService.java
    │       │   ├── FinancialRecordService.java
    │       │   └── DashboardService.java
    │       └── FinanceApplication.java
    └── resources/
        └── application.properties
```

---

## Developer
**Sweekrithi Shetty**  
Backend Developer Intern Assessment  
Zorvyn FinTech Pvt. Ltd.
