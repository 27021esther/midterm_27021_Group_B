-- ============================================================
-- ALTER LOCATIONS TABLE - Remove id and level columns
-- ============================================================

-- Step 1: Drop all foreign key constraints that reference locations.id
ALTER TABLE customers DROP CONSTRAINT IF EXISTS fk_customer_location;
ALTER TABLE users DROP CONSTRAINT IF EXISTS fk_user_location;

-- Step 2: Drop the primary key constraint on id
ALTER TABLE locations DROP CONSTRAINT IF EXISTS locations_pkey;

-- Step 3: Drop the id column
ALTER TABLE locations DROP COLUMN IF EXISTS id;

-- Step 4: Drop the level column (if it exists)
ALTER TABLE locations DROP COLUMN IF EXISTS level;

-- Step 5: Rename parent_id column if needed and make it the primary key
ALTER TABLE locations ADD PRIMARY KEY (parent_id);

-- Step 6: Ensure parent_id has UUID default generation
ALTER TABLE locations ALTER COLUMN parent_id SET DEFAULT uuid_generate_v4();

-- Step 7: Rename columns in customers and users tables to match
ALTER TABLE customers RENAME COLUMN location_id TO location_parent_id;
ALTER TABLE users RENAME COLUMN location_id TO location_parent_id;

-- Step 8: Re-add foreign key constraints
ALTER TABLE customers 
    ADD CONSTRAINT fk_customer_location 
    FOREIGN KEY (location_parent_id) 
    REFERENCES locations (parent_id) ON DELETE CASCADE;

ALTER TABLE users 
    ADD CONSTRAINT fk_user_location 
    FOREIGN KEY (location_parent_id) 
    REFERENCES locations (parent_id) ON DELETE CASCADE;

-- Step 9: Verify the structure
SELECT column_name, data_type, is_nullable, column_default
FROM information_schema.columns
WHERE table_name = 'locations'
ORDER BY ordinal_position;
