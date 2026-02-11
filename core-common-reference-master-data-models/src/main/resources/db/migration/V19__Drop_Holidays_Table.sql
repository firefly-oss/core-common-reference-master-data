-- V19__Drop_Holidays_Table.sql
-- ---------------------------------------
-- This script drops the holidays table and its relationships.

-- Drop foreign key constraints
ALTER TABLE holidays DROP CONSTRAINT IF EXISTS fk_holidays_division;

-- Drop indexes
DROP INDEX IF EXISTS idx_holidays_country_id;
DROP INDEX IF EXISTS idx_holidays_division_id;

-- Drop the holidays table
DROP TABLE IF EXISTS holidays;

-- End of V19__Drop_Holidays_Table.sql