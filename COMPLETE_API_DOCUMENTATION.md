# Complete CRUD API Documentation

## 📍 1. LOCATION APIs

### Create Location
- **POST** `http://localhost:8081/api/locations`
```json
{
  "code": "KIG",
  "locationType": "PROVINCE",
  "name": "Kigali City",
  "locationId": null
}
```

### Get All Locations
- **GET** `http://localhost:8081/api/locations`

### Get Location by ID
- **GET** `http://localhost:8081/api/locations/{id}`

### Get Locations by Type
- **GET** `http://localhost:8081/api/locations/by-type/PROVINCE`

### Update Location
- **PUT** `http://localhost:8081/api/locations/{id}`
```json
{
  "code": "KIG",
  "locationType": "PROVINCE",
  "name": "Kigali City Updated",
  "locationId": null
}
```

### Delete Location
- **DELETE** `http://localhost:8081/api/locations/{id}`

---

## 🔐 2. ROLE APIs

### Create Role
- **POST** `http://localhost:8081/api/roles`
```json
{
  "roleName": "STUDENT_USER",
  "description": "Regular student user"
}
```

### Get All Roles
- **GET** `http://localhost:8081/api/roles`

### Get Role by ID
- **GET** `http://localhost:8081/api/roles/{id}`

### Update Role
- **PUT** `http://localhost:8081/api/roles/{id}`
```json
{
  "roleName": "STUDENT_USER",
  "description": "Updated description"
}
```

### Delete Role
- **DELETE** `http://localhost:8081/api/roles/{id}`

---

## 👥 3. CUSTOMER APIs

### Create Customer
- **POST** `http://localhost:8081/api/customers?locationId={uuid}`
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "phone": "0788123456"
}
```

### Get All Customers
- **GET** `http://localhost:8081/api/customers`

### Get Customer by ID
- **GET** `http://localhost:8081/api/customers/{id}`

### Update Customer
- **PUT** `http://localhost:8081/api/customers/{id}?locationId={uuid}`
```json
{
  "firstName": "John",
  "lastName": "Doe Updated",
  "email": "john.updated@example.com",
  "phone": "0788999999"
}
```

### Delete Customer
- **DELETE** `http://localhost:8081/api/customers/{id}`

---

## 👤 4. USER APIs

### Create User
- **POST** `http://localhost:8081/api/users`
```json
{
  "firstName": "Esther",
  "lastName": "IRADUKUNDA",
  "email": "esther@auca.ac.rw",
  "username": "esther27021",
  "password": "esther123",
  "locationCode": "V01",
  "roleNames": ["STUDENT_USER"],
  "profile": {
    "phoneNumber": "0791431286",
    "nationalId": "1200380012345678",
    "bio": "AUCA Student"
  }
}
```

### Get All Users (with pagination)
- **GET** `http://localhost:8081/api/users?page=0&size=5&sortBy=firstName&sortDir=asc`

### Get User by ID
- **GET** `http://localhost:8081/api/users/{id}`

### Check if User Exists by Email
- **GET** `http://localhost:8081/api/users/exists?email=esther@auca.ac.rw`

---

## ⚙️ 5. FEATURE APIs

### Create Feature
- **POST** `http://localhost:8081/api/features`
```json
{
  "name": "Air Conditioning",
  "description": "Climate control system"
}
```

### Get All Features
- **GET** `http://localhost:8081/api/features`

### Get Feature by ID
- **GET** `http://localhost:8081/api/features/{id}`

### Update Feature
- **PUT** `http://localhost:8081/api/features/{id}`
```json
{
  "name": "Air Conditioning",
  "description": "Updated climate control system"
}
```

### Delete Feature
- **DELETE** `http://localhost:8081/api/features/{id}`

---

## 🚗 6. VEHICLE APIs

### Create Vehicle
- **POST** `http://localhost:8081/api/vehicles`
```json
{
  "make": "Toyota",
  "model": "Corolla",
  "year": 2020,
  "price": 15000,
  "vin": "CH123456",
  "status": "AVAILABLE",
  "featureIds": [1, 2]
}
```

### Get All Vehicles (with pagination)
- **GET** `http://localhost:8081/api/vehicles?page=0&size=5&sortBy=price&sortDir=asc`

### Get Vehicle by ID
- **GET** `http://localhost:8081/api/vehicles/{id}`

### Check if Vehicle Exists by VIN
- **GET** `http://localhost:8081/api/vehicles/exists?vin=CH123456`

### Update Vehicle
- **PUT** `http://localhost:8081/api/vehicles/{id}`
```json
{
  "make": "Toyota",
  "model": "Corolla Updated",
  "year": 2021,
  "price": 16000,
  "vin": "CH123456",
  "status": "SOLD",
  "featureIds": [1, 2, 3]
}
```

### Delete Vehicle
- **DELETE** `http://localhost:8081/api/vehicles/{id}`

---

## 💰 7. TRANSACTION APIs

### Create Transaction
- **POST** `http://localhost:8081/api/transactions`
```json
{
  "customerId": 1,
  "vehicleId": 1,
  "transactionType": "SALE",
  "amount": 15000,
  "transactionDate": "2026-03-12"
}
```

### Get All Transactions (with pagination)
- **GET** `http://localhost:8081/api/transactions?page=0&size=5&sortBy=transactionDate&sortDir=desc`

### Get Transaction by ID
- **GET** `http://localhost:8081/api/transactions/{id}`

### Get Customer's Transactions
- **GET** `http://localhost:8081/api/customers/{id}/transactions`

### Update Transaction
- **PUT** `http://localhost:8081/api/transactions/{id}`
```json
{
  "customerId": 1,
  "vehicleId": 1,
  "transactionType": "SALE",
  "amount": 16000,
  "transactionDate": "2026-03-13"
}
```

### Delete Transaction
- **DELETE** `http://localhost:8081/api/transactions/{id}`

---

## 📋 Testing Order in Postman

### 1. Setup Locations (Hierarchy)
```
POST Province → POST District → POST Sector → POST Cell → POST Village
```

### 2. Setup Roles
```
POST Role (STUDENT_USER)
POST Role (ADMIN)
POST Role (MANAGER)
```

### 3. Setup Features
```
POST Feature (Air Conditioning)
POST Feature (GPS)
POST Feature (Leather Seats)
```

### 4. Create Users
```
POST User (with locationCode and roleNames)
```

### 5. Create Customers
```
POST Customer (with locationId)
```

### 6. Create Vehicles
```
POST Vehicle (with featureIds)
```

### 7. Create Transactions
```
POST Transaction (with customerId and vehicleId)
```

### 8. Test CRUD Operations
```
GET All → GET by ID → PUT Update → DELETE
```

---

## 🎯 HTTP Status Codes

- **200 OK** - Successful GET/PUT
- **201 Created** - Successful POST
- **204 No Content** - Successful DELETE
- **400 Bad Request** - Invalid input
- **404 Not Found** - Resource not found
- **500 Internal Server Error** - Server error

---

## ✅ Requirements Coverage

| Requirement | Implementation |
|-------------|----------------|
| CRUD Operations | All entities have POST, GET, PUT, DELETE |
| Pagination & Sorting | Users, Vehicles, Transactions |
| Many-to-Many | Vehicle ↔ Feature, User ↔ Role |
| One-to-Many | Customer → Transactions |
| One-to-One | User → UserProfile |
| existsBy() | User (email), Vehicle (VIN) |
| Hierarchical Location | Self-referencing Location table |
| UUID Primary Key | Location table |

---

## 🚀 Quick Start

1. Start Spring Boot: `.\mvnw.cmd spring-boot:run`
2. Open Postman
3. Create locations hierarchy
4. Create roles and features
5. Create users and customers
6. Create vehicles
7. Create transactions
8. Test all CRUD operations

Done! 🎉
