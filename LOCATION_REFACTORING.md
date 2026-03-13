# Location Hierarchy Refactoring

## Summary
Refactored the location system from 5 separate tables (Province, District, Sector, Cell, Village) to a single self-referencing `Location` table with hierarchical structure.

## Changes Made

### 1. Database Schema (vehicle_trading_db.sql)
- **Removed tables**: provinces, districts, sectors, cells, villages
- **Updated locations table** with self-referencing structure:
  - `id` (BIGSERIAL PRIMARY KEY) - Auto-generated unique ID
  - `name` (VARCHAR(100)) - Location name
  - `code` (VARCHAR(20) UNIQUE) - Unique code for each location
  - `level` (VARCHAR(20)) - Hierarchy level (PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE)
  - `parent_id` (BIGINT) - Self-referencing foreign key to parent location
- **Updated customers table**: Now references `location_id` only (removed `village_id`)

### 2. Entity Changes

#### Location.java
- Added self-referencing `parent` relationship (ManyToOne)
- Added `children` collection (OneToMany)
- Added fields: `name`, `code`, `level`
- Removed: `district`, `sector`, `street`, `province` reference
- Changed Customer relationship from OneToOne to OneToMany

#### Customer.java
- Changed from Village reference to Location reference
- Simplified to single ManyToOne relationship with Location
- Removed: `village` field and `location` OneToOne field

### 3. DTO Changes

#### LocationDTO.java
- Updated fields: `id`, `name`, `code`, `level`, `parentId`, `parentName`
- Removed: `district`, `sector`, `street`, `provinceId`, `provinceName`

#### CustomerDTO.java
- No changes needed (already uses LocationDTO)

### 4. Repository Changes

#### LocationRepository.java
- Added: `findByParentId(Long parentId)` - Get all children of a location
- Added: `findByLevel(String level)` - Get all locations at a specific level
- Added: `existsByCode(String code)` - Check if code exists
- Removed: `findByProvinceId()`, `existsByDistrictAndSector()`

#### CustomerRepository.java
- Simplified to only: `existsByEmail(String email)`
- Removed all location-based query methods

### 5. Service Changes

#### LocationService.java
- Updated `saveLocation()` to handle parent relationship
- Added `getByParentId()` and `getByLevel()` methods
- Removed province-related dependencies

#### CustomerService.java
- Updated `saveCustomer()` to accept `locationId` instead of `villageId`
- Removed all location-based query methods
- Simplified to basic CRUD operations

### 6. Controller Changes

#### LocationController.java
- Updated POST endpoint to accept Location without separate provinceId
- Added `/by-parent/{parentId}` endpoint
- Added `/by-level/{level}` endpoint
- Removed province-specific endpoints

#### CustomerController.java
- Updated POST endpoint to accept `locationId` instead of `villageId`
- Removed all location-based query endpoints

### 7. Deleted Files
**Entities**: Province.java, District.java, Sector.java, Cell.java, Village.java
**DTOs**: ProvinceDTO.java, DistrictDTO.java, SectorDTO.java, CellDTO.java, VillageDTO.java
**Repositories**: ProvinceRepository.java, DistrictRepository.java, SectorRepository.java, CellRepository.java, VillageRepository.java
**Services**: ProvinceService.java, DistrictService.java, SectorService.java, CellService.java, VillageService.java
**Controllers**: ProvinceController.java, DistrictController.java, SectorController.java, CellController.java, VillageController.java

## Hierarchy Structure

```
Province (level=PROVINCE, parent_id=NULL, code auto-generated)
  └── District (level=DISTRICT, parent_id=province.id)
      └── Sector (level=SECTOR, parent_id=district.id)
          └── Cell (level=CELL, parent_id=sector.id)
              └── Village (level=VILLAGE, parent_id=cell.id)
```

## Sample Data Structure

The SQL includes sample data showing the hierarchy:
- 5 Provinces (Kigali City, Southern, Northern, Eastern, Western)
- 3 Districts under Kigali (Gasabo, Kicukiro, Nyarugenge)
- 3 Sectors (Kimironko, Niboye, Nyarugenge)
- 3 Cells (Gikomero, Kagarama, Gatsata)
- 3 Villages (Bibare, Kabeza, Rugarama)

## API Changes

### Location Endpoints
- `POST /api/locations` - Create location (body includes parent reference)
- `GET /api/locations` - Get all locations
- `GET /api/locations/{id}` - Get location by ID
- `GET /api/locations/by-parent/{parentId}` - Get children of a location
- `GET /api/locations/by-level/{level}` - Get all locations at a level (e.g., PROVINCE)

### Customer Endpoints
- `POST /api/customers?locationId={id}` - Create customer with location
- `GET /api/customers` - Get all customers
- `GET /api/customers/{id}` - Get customer by ID

## Benefits
1. **Simplified structure**: One table instead of five
2. **Flexible hierarchy**: Easy to add new levels if needed
3. **Auto-generated IDs**: Province codes are now auto-generated unique IDs
4. **Cleaner code**: Removed redundant entities, services, and controllers
5. **Easier maintenance**: Single location management system
