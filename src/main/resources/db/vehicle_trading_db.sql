-- ============================================================
-- DROP ALL OLD TABLES
-- ============================================================
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS vehicle_features CASCADE;
DROP TABLE IF EXISTS vehicles CASCADE;
DROP TABLE IF EXISTS features CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS user_profiles CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS customers CASCADE;
DROP TABLE IF EXISTS locations CASCADE;

-- ============================================================
-- CREATE NEW SCHEMA - SIMPLIFIED LOCATION TABLE
-- ============================================================

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- TABLE: locations (id, code, location_type, name)
CREATE TABLE IF NOT EXISTS locations (
    id             UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code           VARCHAR(20) NOT NULL UNIQUE,
    location_type  VARCHAR(20) NOT NULL,
    name           VARCHAR(100) NOT NULL
);

-- TABLE: customers
CREATE TABLE IF NOT EXISTS customers (
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(100) NOT NULL,
    last_name   VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    phone       VARCHAR(20),
    location_id UUID NOT NULL,
    CONSTRAINT fk_customer_location FOREIGN KEY (location_id)
        REFERENCES locations (id) ON DELETE CASCADE
);

-- TABLE: user_profiles
CREATE TABLE IF NOT EXISTS user_profiles (
    id           BIGSERIAL PRIMARY KEY,
    phone_number VARCHAR(20),
    national_id  VARCHAR(50),
    bio          TEXT
);

-- TABLE: users
CREATE TABLE IF NOT EXISTS users (
    id              BIGSERIAL PRIMARY KEY,
    first_name      VARCHAR(100) NOT NULL,
    last_name       VARCHAR(100) NOT NULL,
    email           VARCHAR(150) NOT NULL UNIQUE,
    username        VARCHAR(50)  NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    location_id     UUID NOT NULL,
    user_profile_id BIGINT UNIQUE,
    CONSTRAINT fk_user_location FOREIGN KEY (location_id)
        REFERENCES locations (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_profile FOREIGN KEY (user_profile_id)
        REFERENCES user_profiles (id) ON DELETE SET NULL
);

-- TABLE: roles
CREATE TABLE IF NOT EXISTS roles (
    id          BIGSERIAL PRIMARY KEY,
    role_name   VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- TABLE: user_roles
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_ur_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_ur_role FOREIGN KEY (role_id)
        REFERENCES roles (id) ON DELETE CASCADE
);

-- TABLE: features
CREATE TABLE IF NOT EXISTS features (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- TABLE: vehicles
CREATE TABLE IF NOT EXISTS vehicles (
    id               BIGSERIAL PRIMARY KEY,
    make             VARCHAR(100)   NOT NULL,
    model            VARCHAR(100)   NOT NULL,
    manufacture_year INTEGER        NOT NULL,
    price            NUMERIC(15, 2) NOT NULL,
    vin              VARCHAR(50)    NOT NULL UNIQUE,
    status           VARCHAR(20)    NOT NULL CHECK (status IN ('AVAILABLE', 'SOLD', 'RESERVED'))
);

-- TABLE: vehicle_features
CREATE TABLE IF NOT EXISTS vehicle_features (
    vehicle_id BIGINT NOT NULL,
    feature_id BIGINT NOT NULL,
    PRIMARY KEY (vehicle_id, feature_id),
    CONSTRAINT fk_vf_vehicle FOREIGN KEY (vehicle_id)
        REFERENCES vehicles (id) ON DELETE CASCADE,
    CONSTRAINT fk_vf_feature FOREIGN KEY (feature_id)
        REFERENCES features (id) ON DELETE CASCADE
);

-- TABLE: transactions
CREATE TABLE IF NOT EXISTS transactions (
    id               BIGSERIAL PRIMARY KEY,
    transaction_date TIMESTAMP      NOT NULL,
    sale_price       NUMERIC(15, 2) NOT NULL,
    transaction_type VARCHAR(10)    NOT NULL CHECK (transaction_type IN ('BUY', 'SELL')),
    customer_id      BIGINT         NOT NULL,
    vehicle_id       BIGINT         NOT NULL,
    CONSTRAINT fk_transaction_customer FOREIGN KEY (customer_id)
        REFERENCES customers (id) ON DELETE CASCADE,
    CONSTRAINT fk_transaction_vehicle  FOREIGN KEY (vehicle_id)
        REFERENCES vehicles (id) ON DELETE CASCADE
);

-- ============================================================
-- SAMPLE DATA
-- ============================================================

-- Provinces (UUID auto-generated)
INSERT INTO locations (code, location_type, name) VALUES
    ('KIG', 'PROVINCE', 'Kigali City'),
    ('S', 'PROVINCE', 'Southern Province'),
    ('N', 'PROVINCE', 'Northern Province'),
    ('E', 'PROVINCE', 'Eastern Province'),
    ('W', 'PROVINCE', 'Western Province');

-- Districts
INSERT INTO locations (code, location_type, name) VALUES
    ('GAS', 'DISTRICT', 'Gasabo'),
    ('KIC', 'DISTRICT', 'Kicukiro'),
    ('NDO', 'DISTRICT', 'Nyarugenge');

-- Sectors
INSERT INTO locations (code, location_type, name) VALUES
    ('KIM', 'SECTOR', 'Kimironko'),
    ('NIB', 'SECTOR', 'Niboye'),
    ('NYA', 'SECTOR', 'Nyarugenge');

-- Cells
INSERT INTO locations (code, location_type, name) VALUES
    ('GIK', 'CELL', 'Gikomero'),
    ('KAG', 'CELL', 'Kagarama'),
    ('GSA', 'CELL', 'Gatsata');

-- Villages
INSERT INTO locations (code, location_type, name) VALUES
    ('V01', 'VILLAGE', 'Bibare'),
    ('V02', 'VILLAGE', 'Kabeza'),
    ('V03', 'VILLAGE', 'Rugarama');

-- Roles
INSERT INTO roles (role_name, description) VALUES
    ('ADMIN', 'Administrator with full access'),
    ('USER', 'Regular user with limited access'),
    ('MANAGER', 'Manager with elevated privileges');

-- Features
INSERT INTO features (name, description) VALUES
    ('Sunroof', 'Panoramic sunroof'),
    ('GPS', 'Built-in navigation system'),
    ('Leather Seats', 'Premium leather interior'),
    ('Backup Camera', 'Rear-view camera'),
    ('Bluetooth', 'Wireless audio connectivity');
