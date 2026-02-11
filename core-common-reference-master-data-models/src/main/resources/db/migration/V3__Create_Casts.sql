-- V3__Create_Casts.sql
-- --------------------
-- This script creates implicit casts from varchar to the ENUM types
-- using WITH INOUT. That allows inserting/updating enum fields from varchar
-- without explicit casting.

---------------------------
-- 1) Cast for status_enum
---------------------------
CREATE CAST (varchar AS status_enum)
    WITH INOUT
    AS IMPLICIT;

---------------------------
-- 2) Cast for region_enum
---------------------------
CREATE CAST (varchar AS region_enum)
    WITH INOUT
    AS IMPLICIT;

--------------------------------
-- 3) Cast for holiday_type_enum
--------------------------------
CREATE CAST (varchar AS holiday_type_enum)
    WITH INOUT
    AS IMPLICIT;
