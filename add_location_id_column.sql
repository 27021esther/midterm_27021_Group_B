-- Add location_id column to locations table
ALTER TABLE locations ADD COLUMN location_id UUID;

-- Add self-referencing foreign key constraint
ALTER TABLE locations 
    ADD CONSTRAINT fk_location_parent 
    FOREIGN KEY (location_id) 
    REFERENCES locations (id) ON DELETE CASCADE;

-- Verify the new structure
SELECT column_name, data_type, is_nullable 
FROM information_schema.columns 
WHERE table_name = 'locations' 
ORDER BY ordinal_position;
