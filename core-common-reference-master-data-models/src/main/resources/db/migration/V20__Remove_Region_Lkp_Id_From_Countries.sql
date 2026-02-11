-- V20__Remove_Region_Lkp_Id_From_Countries.sql
-- ---------------------------------------
-- This script removes the region_lkp_id column from the countries table
-- as we're using the region enum directly instead of a lookup reference.

-- Remove the region_lkp_id column from the countries table
ALTER TABLE countries DROP COLUMN IF EXISTS region_lkp_id;