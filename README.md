# Vehicle Trading Application

---

## 👤 Student Profile

- **Name**: IRADUKUNDA Esther
- **Student ID**: 27021
- **Course**: Web Technology - Group B

---

A RESTful Spring Boot application for managing vehicle trading operations with location-based hierarchy, user management, vehicle inventory, and transaction tracking.

## Database Configuration

- **Database Name**: `vehicle_trading_db`
- **Database Type**: PostgreSQL
- **Port**: 8081 (as configured in application.properties)

## API Endpoints

### 📍 1. LOCATION APIs (Requirement #2)

#### Province Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `http://localhost:8081/api/locations/provinces` | Create a province |
| GET | `http://localhost:8081/api/locations/provinces` | Get all provinces |

#### District Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `http://localhost:8081/api/locations/districts` | Create a district |
| GET | `http://localhost:8081/api/locations/districts` | Get all districts |

#### Sector Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `http://localhost:8081/api/locations/sectors` | Create a sector |
| GET | `http://localhost:8081/api/locations/sectors` | Get all sectors |

#### Cell Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `http://localhost:8081/api/locations/cells` | Create a cell |
| GET | `http://localhost:8081/api/locations/cells` | Get all cells |

#### Village Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `http://localhost:8081/api/locations/villages` | Create a village |
| GET | `http://localhost:8081/api/locations/villages` | Get all villages |

---

### 👤 2. USER APIs (Requirements #3, #6, #7, #8)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `http://localhost:8081/api/users` | Create a new user |
| GET | `http://localhost:8081/api/users?page=0&size=5&sortBy=firstName&sortDir=asc` | Get all users with pagination & sorting |
| GET | `http://localhost:8081/api/users/{id}` | Get user by ID |
| GET | `http://localhost:8081/api/users/by-province/KIG` | Get users by province code |
| GET | `http://localhost:8081/api/users/by-province/Kigali City` | Get users by province name |
| GET | `http://localhost:8081/api/users/exists?email=patrick@auca.ac.rw` | Check if user exists by email |

---

### 🔐 3. ROLE APIs (Requirement #4 — Many-to-Many)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `http://localhost:8081/api/roles` | Create a new role |
| GET | `http://localhost:8081/api/roles` | Get all roles |

---

### 🚗 4. VEHICLE APIs (Requirement #3, #5)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `http://localhost:8081/api/vehicles` | Create a new vehicle |
| GET | `http://localhost:8081/api/vehicles?page=0&size=5&sortBy=price&sortDir=asc` | Get all vehicles with pagination & sorting |
| GET | `http://localhost:8081/api/vehicles/{id}` | Get vehicle by ID |
| GET | `http://localhost:8081/api/vehicles/exists?plateNumber=RAB123A` | Check if vehicle exists by plate number |

---

### 👥 5. CUSTOMER APIs (Requirement #3, #7)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `http://localhost:8081/api/customers` | Create a new customer |
| GET | `http://localhost:8081/api/customers?page=0&size=5&sortBy=firstName&sortDir=asc` | Get all customers with pagination & sorting |
| GET | `http://localhost:8081/api/customers/{id}` | Get customer by ID |
| GET | `http://localhost:8081/api/customers/exists?nationalId=1200080012345678` | Check if customer exists by national ID |

---

### 💰 6. TRANSACTION APIs (Requirement #3, #5 — One-to-Many)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `http://localhost:8081/api/transactions` | Create a new transaction |
| GET | `http://localhost:8081/api/transactions?page=0&size=5&sortBy=transactionDate&sortDir=desc` | Get all transactions with pagination & sorting |
| GET | `http://localhost:8081/api/transactions/{id}` | Get transaction by ID |
| GET | `http://localhost:8081/api/customers/{id}/transactions` | Get all transactions for a customer |

---

### ⚙️ 7. FEATURE APIs (Requirement #4 — Many-to-Many with Vehicle)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `http://localhost:8081/api/features` | Create a new feature |
| GET | `http://localhost:8081/api/features` | Get all features |

---

## Request Bodies

### POST Province
```json
{
  "provinceCode": "KIG",
  "provinceName": "Kigali City"
}
```

### POST District
```json
{
  "districtCode": "GAS",
  "districtName": "Gasabo",
  "provinceCode": "KIG"
}
```

### POST Sector
```json
{
  "sectorCode": "REM",
  "sectorName": "Remera",
  "districtCode": "GAS"
}
```

### POST Cell
```json
{
  "cellCode": "KAC",
  "cellName": "Kacyiru",
  "sectorCode": "REM"
}
```

### POST Village
```json
{
  "villageCode": "V001",
  "villageName": "Nyarutarama",
  "cellCode": "KAC"
}
```

### POST Role
```json
{
  "roleName": "STUDENT_USER",
  "description": "Regular user"
}
```

### POST User (villageCode only — hierarchy auto-resolved)
```json
{
  "firstName": "esther",
  "lastName": "IRADUKUNDA",
  "email": "esther@auca.ac.rw",
  "username": "ESTHER27021",
  "password": "esther123",
  "villageCode": "V001",
  "roleNames": ["STUDENT_USER"],
  "profile": {
    "phoneNumber": "0791431286",
    "nationalId": "1200380012345678",
    "bio": "AUCA Student"
  }
}
```

### POST Customer
```json
{
  "firstName": "esther",
  "lastName": "IRADUKUNDA",
  "email": "esther@gmail.com",
  "phoneNumber": "0789000000",
  "nationalId": "1199080012345678",
  "villageCode": "V001"
}
```

### POST Feature
```json
{
  "featureName": "Air Conditioning",
  "description": "Climate control system"
}
```

### POST Vehicle
```json
{
  "plateNumber": "RAB123A",
  "chassisNumber": "CH123456",
  "model": "Toyota Corolla",
  "year": 2020,
  "price": 15000,
  "color": "White",
  "status": "AVAILABLE",
  "featureIds": [1, 2]
}
```

### POST Transaction
```json
{
  "customerId": 1,
  "vehicleId": 1,
  "transactionType": "SALE",
  "amount": 15000,
  "transactionDate": "2026-03-12"
}
```

---

## ✅ Correct Order to Run in Postman

```
1.  POST Province
2.  POST District
3.  POST Sector
4.  POST Cell
5.  POST Village
6.  POST Role
7.  POST User        ← uses villageCode only
8.  POST Customer    ← uses villageCode only
9.  POST Feature
10. POST Vehicle     ← uses featureIds (Many-to-Many)
11. POST Transaction ← uses customerId + vehicleId
12. GET all with pagination + sorting
13. GET users by province code or name
14. GET exists checks
```

---

## How Each Requirement Is Covered

| Requirement | API That Proves It |
|-------------|-------------------|
| ERD 5+ tables | Province, District, Sector, Cell, Village, User, Customer, Vehicle, Feature, Transaction |
| Saving Location | POST Province → Village chain |
| Sorting + Pagination | All GET APIs with ?page=&size=&sortBy=&sortDir= |
| Many-to-Many | Vehicle ↔ Feature, User ↔ Role |
| One-to-Many | Customer → Transactions |
| One-to-One | User → UserProfile |
| existsBy() | GET /exists?email= or /exists?plateNumber= |
| Users by Province | GET /api/users/by-province/KIG |

---

## Project Structure

```
vehicle-trading/
├── src/main/java/com/vehicletrading/vehicle_trading/
│   ├── controller/      # REST API controllers
│   ├── service/        # Business logic
│   ├── repository/     # Data access layer
│   ├── entity/         # JPA entities
│   └── dto/            # Data Transfer Objects
├── src/main/resources/
│   ├── application.properties
│   └── db/             # SQL scripts
└── pom.xml             # Maven dependencies
```

---

## Technology Stack

- **Framework**: Spring Boot
- **Database**: PostgreSQL (vehicle_trading_db)
- **Build Tool**: Maven
- **API Style**: RESTful
- **API Testing Tool**: Postman

---

## 🚀 How to Test APIs in Postman

### Step 1: Open Postman
1. Download and install Postman from https://www.postman.com/
2. Create a new Collection (e.g., "Vehicle Trading API")
3. Create a new Environment (e.g., "Local Dev") with variable:
   - `baseUrl` = `http://localhost:8081`

### Step 2: Create Each API Request

For each API below:
1. Click "Add Request" in your collection
2. Set the **Method** (GET, POST, etc.)
3. Enter the URL: `{{baseUrl}}` + endpoint path
4. For POST/PUT requests, go to **Body** tab → select **raw** → choose **JSON**
5. Paste the JSON request body
6. Click **Send** to test

---

## 📋 API Testing Order (Must Create in This Sequence)

```
1.  POST Province     → Create first province
2.  POST District     → Link to province
3.  POST Sector       → Link to district
4.  POST Cell         → Link to sector
5.  POST Village      → Link to cell
6.  POST Role         → Create roles (before users)
7.  POST User         → Create user with villageCode & roleNames
8.  POST Customer     → Create customer with villageCode
9.  POST Feature      → Create vehicle features
10. POST Vehicle      → Create vehicle with featureIds
11. POST Transaction  → Link customer + vehicle
12. GET all           → Test pagination + sorting
13. GET by province   → Test hierarchical queries
14. GET exists        → Test existence checks
```

---

## 🔧 Detailed Postman Request Setup

### 📍 1. LOCATION APIs (Province, District, Sector, Cell, Village)

**POST Province**
- Method: `POST`
- URL: `{{baseUrl}}/api/locations/provinces`
- Body (JSON):
```json
{
  "provinceCode": "KIG",
  "provinceName": "Kigali City"
}
```

**GET Provinces**
- Method: `GET`
- URL: `{{baseUrl}}/api/locations/provinces`

**POST District**
- Method: `POST`
- URL: `{{baseUrl}}/api/locations/districts`
- Body (JSON):
```json
{
  "districtCode": "GAS",
  "districtName": "Gasabo",
  "provinceCode": "KIG"
}
```

**GET Districts**
- Method: `GET`
- URL: `{{baseUrl}}/api/locations/districts`

**POST Sector**
- Method: `POST`
- URL: `{{baseUrl}}/api/locations/sectors`
- Body (JSON):
```json
{
  "sectorCode": "REM",
  "sectorName": "Remera",
  "districtCode": "GAS"
}
```

**GET Sectors**
- Method: `GET`
- URL: `{{baseUrl}}/api/locations/sectors`

**POST Cell**
- Method: `POST`
- URL: `{{baseUrl}}/api/locations/cells`
- Body (JSON):
```json
{
  "cellCode": "KAC",
  "cellName": "Kacyiru",
  "sectorCode": "REM"
}
```

**GET Cells**
- Method: `GET`
- URL: `{{baseUrl}}/api/locations/cells`

**POST Village**
- Method: `POST`
- URL: `{{baseUrl}}/api/locations/villages`
- Body (JSON):
```json
{
  "villageCode": "V001",
  "villageName": "Nyarutarama",
  "cellCode": "KAC"
}
```

**GET Villages**
- Method: `GET`
- URL: `{{baseUrl}}/api/locations/villages`

---

### 🔐 2. ROLE APIs

**POST Role**
- Method: `POST`
- URL: `{{baseUrl}}/api/roles`
- Body (JSON):
```json
{
  "roleName": "STUDENT_USER",
  "description": "Regular user"
}
```

**GET Roles**
- Method: `GET`
- URL: `{{baseUrl}}/api/roles`

---

### 👤 3. USER APIs

**POST User** (Create with villageCode + roleNames + profile)
- Method: `POST`
- URL: `{{baseUrl}}/api/users`
- Body (JSON):
```json
{
  "firstName": "esther",
  "lastName": "IRADUKUNDA",
  "email": "esther@auca.ac.rw",
  "username": "patrick27021",
  "password": "esther123",
  "villageCode": "V001",
  "roleNames": ["STUDENT_USER"],
  "profile": {
    "phoneNumber": "0791431286",
    "nationalId": "1200380012345678",
    "bio": "AUCA Student"
  }
}
```

**GET Users** (With Pagination & Sorting)
- Method: `GET`
- URL: `{{baseUrl}}/api/users?page=0&size=5&sortBy=firstName&sortDir=asc`
- Params: page=0, size=5, sortBy=firstName, sortDir=asc

**GET User by ID**
- Method: `GET`
- URL: `{{baseUrl}}/api/users/1`

**GET Users by Province Code**
- Method: `GET`
- URL: `{{baseUrl}}/api/users/by-province/KIG`

**GET Users by Province Name**
- Method: `GET`
- URL: `{{baseUrl}}/api/users/by-province/Kigali City`

**GET Check if User Exists by Email**
- Method: `GET`
- URL: `{{baseUrl}}/api/users/exists?email=esther@auca.ac.rw`
- Params: email=esther@auca.ac.rw

---

### 👥 4. CUSTOMER APIs

**POST Customer**
- Method: `POST`
- URL: `{{baseUrl}}/api/customers`
- Body (JSON):
```json
{
  "firstName": "esther",
  "lastName": "IRADUKUNDA",
  "email": "esther@gmail.com",
  "phoneNumber": "0789000000",
  "nationalId": "1199080012345678",
  "villageCode": "V001"
}
```

**GET Customers** (With Pagination & Sorting)
- Method: `GET`
- URL: `{{baseUrl}}/api/customers?page=0&size=5&sortBy=firstName&sortDir=asc`

**GET Customer by ID**
- Method: `GET`
- URL: `{{baseUrl}}/api/customers/1`

**GET Check if Customer Exists by National ID**
- Method: `GET`
- URL: `{{baseUrl}}/api/customers/exists?nationalId=1199080012345678`

---

### ⚙️ 5. FEATURE APIs

**POST Feature**
- Method: `POST`
- URL: `{{baseUrl}}/api/features`
- Body (JSON):
```json
{
  "name": "Air Conditioning",
  "description": "Climate control system"
}
```

**GET Features**
- Method: `GET`
- URL: `{{baseUrl}}/api/features`

---

### 🚗 6. VEHICLE APIs

**POST Vehicle** (With featureIds)
- Method: `POST`
- URL: `{{baseUrl}}/api/vehicles`
- Body (JSON):
```json
{
  "make": "Toyota",
  "model": "Corolla",
  "year": 2020,
  "price": 15000,
  "vin": "CH123456",
  "status": "AVAILABLE",
  "featureIds": [1]
}
```

**GET Vehicles** (With Pagination & Sorting)
- Method: `GET`
- URL: `{{baseUrl}}/api/vehicles?page=0&size=5&sortBy=price&sortDir=asc`

**GET Vehicle by ID**
- Method: `GET`
- URL: `{{baseUrl}}/api/vehicles/1`

**GET Check if Vehicle Exists by VIN**
- Method: `GET`
- URL: `{{baseUrl}}/api/vehicles/exists?vin=CH123456`

---

### 💰 7. TRANSACTION APIs

**POST Transaction**
- Method: `POST`
- URL: `{{baseUrl}}/api/transactions`
- Body (JSON):
```json
{
  "customerId": 1,
  "vehicleId": 1,
  "transactionType": "SALE",
  "amount": 15000,
  "transactionDate": "2026-03-12"
}
```

**GET Transactions** (With Pagination & Sorting)
- Method: `GET`
- URL: `{{baseUrl}}/api/transactions?page=0&size=5&sortBy=transactionDate&sortDir=desc`

**GET Transaction by ID**
- Method: `GET`
- URL: `{{baseUrl}}/api/transactions/1`

**GET Customer's Transactions**
- Method: `GET`
- URL: `{{baseUrl}}/api/customers/1/transactions`

---

## ✅ Testing Checklist

- [ ] Start Spring Boot application on port 8081
- [ ] Create PostgreSQL database: `vehicle_trading_db`
- [ ] Run POST requests in order (Province → Village → Role → User → etc.)
- [ ] Test GET all with pagination: `?page=0&size=5&sortBy=firstName&sortDir=asc`
- [ ] Test get by ID: `/api/users/1`
- [ ] Test exists check: `/api/users/exists?email=...`
- [ ] Test province filter: `/api/users/by-province/KIG`