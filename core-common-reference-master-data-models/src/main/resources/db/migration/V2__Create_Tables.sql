-- V2__Create_Tables.sql
-- ---------------------
-- This script creates the tables in an order that respects foreign key dependencies.

-----------------------
-- 1) COUNTRIES TABLE
-----------------------
CREATE TABLE IF NOT EXISTS countries (
                           id UUID PRIMARY KEY,
                           iso_code VARCHAR(5) NOT NULL,
                           country_name VARCHAR(100) NOT NULL,
                           region region_enum NOT NULL,
                           status status_enum NOT NULL,
                           svg_flag TEXT
);

----------------------
-- 2) CURRENCIES TABLE
----------------------
CREATE TABLE IF NOT EXISTS currencies (
                            id UUID PRIMARY KEY,
                            iso_code VARCHAR(10) NOT NULL,
                            currency_name VARCHAR(50) NOT NULL,
                            symbol VARCHAR(10) NOT NULL,
                            decimal_precision VARCHAR(10) NOT NULL,
                            status status_enum NOT NULL
);

-------------------
-- 3) BRANCHES TABLE
-------------------
CREATE TABLE IF NOT EXISTS branches (
                          id UUID PRIMARY KEY,
                          branch_code VARCHAR(50) NOT NULL,
                          branch_name VARCHAR(100) NOT NULL,
                          address TEXT,
                          city VARCHAR(100),
                          province VARCHAR(100),
                          country_id UUID REFERENCES countries(id) ON DELETE RESTRICT,
                          phone_number VARCHAR(20),
                          status status_enum NOT NULL
);

--------------------------------
-- 4) BANK/INSTITUTION CODES TABLE
--------------------------------
CREATE TABLE IF NOT EXISTS bank_institution_codes (
                                        id UUID PRIMARY KEY,
                                        bank_name VARCHAR(100) NOT NULL,
                                        swift_code VARCHAR(50),
                                        routing_number VARCHAR(50),
                                        country_id UUID REFERENCES countries(id) ON DELETE RESTRICT,
                                        status status_enum NOT NULL,
                                        svg_icon TEXT
);

-----------------------
-- 5) HOLIDAYS TABLE
-----------------------
CREATE TABLE IF NOT EXISTS holidays (
                          id UUID PRIMARY KEY,
                          country_id UUID REFERENCES countries(id) ON DELETE RESTRICT,
                          holiday_name VARCHAR(100) NOT NULL,
                          holiday_date VARCHAR(20) NOT NULL,
                          holiday_type holiday_type_enum NOT NULL,
                          status status_enum NOT NULL
);

----------------------------
-- 6) LANGUAGE/LOCALE TABLE
----------------------------
CREATE TABLE IF NOT EXISTS language_locale (
                                 id UUID PRIMARY KEY,
                                 language_code VARCHAR(10) NOT NULL,
                                 locale_code VARCHAR(10) NOT NULL,
                                 language_name VARCHAR(100),
                                 region_name VARCHAR(100),
                                 status status_enum NOT NULL
);