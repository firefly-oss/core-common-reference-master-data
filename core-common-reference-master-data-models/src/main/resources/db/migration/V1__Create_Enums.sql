-- V1__Create_Enums.sql
-- ---------------------
-- This script creates the ENUM types needed by the schema.

-- 1) Common status enum
CREATE TYPE status_enum AS ENUM (
    'ACTIVE',
    'INACTIVE'
);

-- 2) Region enum (example values)
CREATE TYPE region_enum AS ENUM (
    'EUROPE',
    'AMERICAS',
    'APAC'
    -- Add more as needed...
);

-- 3) Holiday type enum (example values)
CREATE TYPE holiday_type_enum AS ENUM (
    'NATIONAL',
    'REGIONAL',
    'BANK_ONLY'
    -- Add more as needed...
);