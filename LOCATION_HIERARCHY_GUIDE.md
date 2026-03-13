# Rwanda Location Hierarchy Guide

## Overview
The Location table uses self-referencing to represent the complete Rwandan administrative hierarchy:
- **Province** (Level 1) - parent_id = NULL, has auto-generated ID
- **District** (Level 2) - parent_id = Province ID
- **Sector** (Level 3) - parent_id = District ID
- **Cell** (Level 4) - parent_id = Sector ID
- **Village** (Level 5) - parent_id = Cell ID

## Database Structure

```sql
CREATE TABLE locations (
    id        BIGSERIAL PRIMARY KEY,      -- Auto-generated unique ID
    name      VARCHAR(100) NOT NULL,      -- Location name
    code      VARCHAR(20) UNIQUE NOT NULL, -- Unique code
    level     VARCHAR(20) NOT NULL,       -- PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE
    parent_id BIGINT,                     -- References parent location (NULL for provinces)
    CONSTRAINT fk_location_parent FOREIGN KEY (parent_id)
        REFERENCES locations (id) ON DELETE CASCADE
);
```

## How It Works

### 1. Create a Province (Top Level)
```json
POST /api/locations
{
    "name": "Kigali City",
    "code": "KIG",
    "level": "PROVINCE",
    "parent": null
}
```
**Result**: Province created with auto-generated ID (e.g., id=1), parent_id=NULL

### 2. Create a District (Under Province)
```json
POST /api/locations
{
    "name": "Gasabo",
    "code": "GAS",
    "level": "DISTRICT",
    "parent": {
        "id": 1
    }
}
```
**Result**: District created with auto-generated ID (e.g., id=6), parent_id=1 (references Kigali City)

### 3. Create a Sector (Under District)
```json
POST /api/locations
{
    "name": "Kimironko",
    "code": "KIM",
    "level": "SECTOR",
    "parent": {
        "id": 6
    }
}
```
**Result**: Sector created with auto-generated ID (e.g., id=9), parent_id=6 (references Gasabo)

### 4. Create a Cell (Under Sector)
```json
POST /api/locations
{
    "name": "Gikomero",
    "code": "GIK",
    "level": "CELL",
    "parent": {
        "id": 9
    }
}
```
**Result**: Cell created with auto-generated ID (e.g., id=12), parent_id=9 (references Kimironko)

### 5. Create a Village (Under Cell)
```json
POST /api/locations
{
    "name": "Bibare",
    "code": "V01",
    "level": "VILLAGE",
    "parent": {
        "id": 12
    }
}
```
**Result**: Village created with auto-generated ID (e.g., id=15), parent_id=12 (references Gikomero)

## Complete Hierarchy Example

```
Kigali City (id=1, parent_id=NULL, level=PROVINCE)
  └── Gasabo (id=6, parent_id=1, level=DISTRICT)
      └── Kimironko (id=9, parent_id=6, level=SECTOR)
          └── Gikomero (id=12, parent_id=9, level=CELL)
              └── Bibare (id=15, parent_id=12, level=VILLAGE)
```

## API Endpoints

### Get All Locations
```
GET /api/locations
```
Returns all locations in the system.

### Get Location by ID
```
GET /api/locations/{id}
```
Returns a specific location with its details.

### Get Children of a Location
```
GET /api/locations/by-parent/{parentId}
```
Example: `GET /api/locations/by-parent/1` returns all districts under Kigali City.

### Get All Locations at a Specific Level
```
GET /api/locations/by-level/{level}
```
Examples:
- `GET /api/locations/by-level/PROVINCE` - Returns all provinces
- `GET /api/locations/by-level/DISTRICT` - Returns all districts
- `GET /api/locations/by-level/VILLAGE` - Returns all villages

## Sample Data in Database

The SQL file includes sample data showing the complete hierarchy:

```sql
-- Provinces (parent_id = NULL)
INSERT INTO locations (name, code, level, parent_id) VALUES
    ('Kigali City', 'KIG', 'PROVINCE', NULL),        -- id=1
    ('Southern Province', 'S', 'PROVINCE', NULL),    -- id=2
    ('Northern Province', 'N', 'PROVINCE', NULL),    -- id=3
    ('Eastern Province', 'E', 'PROVINCE', NULL),     -- id=4
    ('Western Province', 'W', 'PROVINCE', NULL);     -- id=5

-- Districts (parent_id = province id)
INSERT INTO locations (name, code, level, parent_id) VALUES
    ('Gasabo', 'GAS', 'DISTRICT', 1),      -- id=6, under Kigali
    ('Kicukiro', 'KIC', 'DISTRICT', 1),    -- id=7, under Kigali
    ('Nyarugenge', 'NDO', 'DISTRICT', 1);  -- id=8, under Kigali

-- Sectors (parent_id = district id)
INSERT INTO locations (name, code, level, parent_id) VALUES
    ('Kimironko', 'KIM', 'SECTOR', 6),     -- id=9, under Gasabo
    ('Niboye', 'NIB', 'SECTOR', 7),        -- id=10, under Kicukiro
    ('Nyarugenge', 'NYA', 'SECTOR', 8);    -- id=11, under Nyarugenge

-- Cells (parent_id = sector id)
INSERT INTO locations (name, code, level, parent_id) VALUES
    ('Gikomero', 'GIK', 'CELL', 9),        -- id=12, under Kimironko
    ('Kagarama', 'KAG', 'CELL', 10),       -- id=13, under Niboye
    ('Gatsata', 'GSA', 'CELL', 11);        -- id=14, under Nyarugenge

-- Villages (parent_id = cell id)
INSERT INTO locations (name, code, level, parent_id) VALUES
    ('Bibare', 'V01', 'VILLAGE', 12),      -- id=15, under Gikomero
    ('Kabeza', 'V02', 'VILLAGE', 13),      -- id=16, under Kagarama
    ('Rugarama', 'V03', 'VILLAGE', 14);    -- id=17, under Gatsata
```

## Using Locations with Customers

When creating a customer, reference the village (or any location level):

```json
POST /api/customers?locationId=15
{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "phone": "0788123456"
}
```

The customer is now linked to:
- Village: Bibare (id=15)
- Cell: Gikomero (id=12) - via parent
- Sector: Kimironko (id=9) - via parent.parent
- District: Gasabo (id=6) - via parent.parent.parent
- Province: Kigali City (id=1) - via parent.parent.parent.parent

## Key Features

✅ **Auto-generated IDs**: Each location gets a unique ID automatically
✅ **Self-referencing**: parent_id references the same locations table
✅ **Hierarchical**: Maintains proper Rwanda administrative structure
✅ **Flexible**: Easy to traverse up (parent) or down (children) the hierarchy
✅ **Cascading**: Deleting a parent deletes all children automatically
✅ **Unique codes**: Each location has a unique code for easy lookup
