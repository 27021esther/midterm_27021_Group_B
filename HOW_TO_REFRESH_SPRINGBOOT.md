# How to Refresh Spring Boot Application

## Option 1: Using IDE (IntelliJ IDEA / Eclipse / VS Code)

### IntelliJ IDEA
1. **Stop the application**: Click the red stop button in the Run panel
2. **Rebuild the project**: 
   - Go to `Build` → `Rebuild Project`
   - Or press `Ctrl + Shift + F9` (Windows/Linux) or `Cmd + Shift + F9` (Mac)
3. **Restart the application**: 
   - Click the green play button
   - Or press `Shift + F10` (Windows/Linux) or `Ctrl + R` (Mac)

### Eclipse / Spring Tool Suite (STS)
1. **Stop the application**: Click the red stop button in Console
2. **Clean and build**:
   - Go to `Project` → `Clean...`
   - Select your project and click OK
3. **Restart**: Right-click on main class → `Run As` → `Spring Boot App`

### VS Code
1. **Stop the application**: Click the trash icon in terminal or press `Ctrl + C`
2. **Restart**: 
   - Press `F5` to debug
   - Or run from terminal: `./mvnw spring-boot:run` (Linux/Mac) or `mvnw.cmd spring-boot:run` (Windows)

---

## Option 2: Using Maven Commands (Terminal/Command Prompt)

### Windows (Command Prompt)
```cmd
# Navigate to project directory
cd c:\Users\HP\Downloads\vehicle-trading\vehicle-trading

# Stop the running application (if running in terminal)
# Press Ctrl + C

# Clean and rebuild
mvnw.cmd clean install

# Run the application
mvnw.cmd spring-boot:run
```

### Linux/Mac (Terminal)
```bash
# Navigate to project directory
cd /path/to/vehicle-trading/vehicle-trading

# Stop the running application (if running in terminal)
# Press Ctrl + C

# Clean and rebuild
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

---

## Option 3: Using Spring Boot DevTools (Hot Reload)

### Enable DevTools for Automatic Restart

1. **Add DevTools dependency** to `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

2. **Configure IntelliJ IDEA for auto-restart**:
   - Go to `File` → `Settings` → `Build, Execution, Deployment` → `Compiler`
   - Check ✅ `Build project automatically`
   - Press `Ctrl + Shift + A` and search for "Registry"
   - Enable ✅ `compiler.automake.allow.when.app.running`

3. **Now just save files** (`Ctrl + S`) and Spring Boot will auto-restart!

---

## Option 4: Rebuild After Database Schema Changes

When you change entities or database schema:

### Step 1: Stop the application
```cmd
# Press Ctrl + C in terminal
```

### Step 2: Update the database (if needed)
```sql
-- Run your updated SQL script
psql -U postgres -d vehicle_trading_db -f src/main/resources/db/vehicle_trading_db.sql
```

Or drop and recreate:
```sql
DROP DATABASE IF EXISTS vehicle_trading_db;
CREATE DATABASE vehicle_trading_db;
\c vehicle_trading_db
\i src/main/resources/db/vehicle_trading_db.sql
```

### Step 3: Clean and rebuild
```cmd
mvnw.cmd clean package
```

### Step 4: Restart application
```cmd
mvnw.cmd spring-boot:run
```

---

## Option 5: Using application.properties for Auto-Update

### For Development (Auto-create/update tables)
Add to `src/main/resources/application.properties`:
```properties
# Auto-update database schema based on entities
spring.jpa.hibernate.ddl-auto=update

# Show SQL queries in console
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

**Options for `spring.jpa.hibernate.ddl-auto`:**
- `none` - No action (production)
- `validate` - Validate schema, no changes
- `update` - Update schema if needed (development)
- `create` - Create schema, drop previous data
- `create-drop` - Create schema, drop when session ends

### For Production (Manual control)
```properties
spring.jpa.hibernate.ddl-auto=none
```

---

## Quick Commands Reference

### Windows
```cmd
# Stop: Ctrl + C
# Clean build: mvnw.cmd clean install
# Run: mvnw.cmd spring-boot:run
# Run in background: start mvnw.cmd spring-boot:run
```

### Linux/Mac
```bash
# Stop: Ctrl + C
# Clean build: ./mvnw clean install
# Run: ./mvnw spring-boot:run
# Run in background: ./mvnw spring-boot:run &
```

---

## Troubleshooting

### Port Already in Use
```cmd
# Windows - Kill process on port 8080
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac - Kill process on port 8080
lsof -ti:8080 | xargs kill -9
```

### Clear Maven Cache
```cmd
# Windows
mvnw.cmd clean
rmdir /s /q target

# Linux/Mac
./mvnw clean
rm -rf target
```

### Force Reimport Dependencies
```cmd
mvnw.cmd clean install -U
```

---

## After Your Location Refactoring

Since you changed the database schema significantly:

1. **Stop Spring Boot** (Ctrl + C)
2. **Drop and recreate database**:
   ```sql
   DROP DATABASE vehicle_trading_db;
   CREATE DATABASE vehicle_trading_db;
   ```
3. **Run the new SQL script**:
   ```cmd
   psql -U postgres -d vehicle_trading_db -f src/main/resources/db/vehicle_trading_db.sql
   ```
4. **Clean and rebuild**:
   ```cmd
   mvnw.cmd clean install
   ```
5. **Restart Spring Boot**:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

Now your application should run without errors! ✅
