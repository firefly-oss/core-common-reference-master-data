-- V18__Drop_Branches_Table.sql
-- ---------------------------------------
-- This script drops the branches table and its relationships.

-- Drop foreign key constraints
ALTER TABLE branches DROP CONSTRAINT IF EXISTS fk_branches_division;

-- Drop indexes
DROP INDEX IF EXISTS idx_branches_country_id;
DROP INDEX IF EXISTS idx_branches_division_id;

-- Drop the branches table
DROP TABLE IF EXISTS branches;

-- End of V18__Drop_Branches_Table.sql