-- V4__Update_Schema_For_New_Data_Model.sql
-- ---------------------------------------
-- This script updates the database schema to match the new data model.

-- Drop existing enum casts
DROP CAST IF EXISTS (varchar AS region_enum);
DROP CAST IF EXISTS (varchar AS holiday_type_enum);

-- Drop existing enum types (after dropping tables that use them)
-- We'll keep status_enum as it's still used

-- Add date_created and date_updated columns to all existing tables
ALTER TABLE countries ADD COLUMN IF NOT EXISTS date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE countries ADD COLUMN IF NOT EXISTS date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE currencies ADD COLUMN IF NOT EXISTS date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE currencies ADD COLUMN IF NOT EXISTS date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE branches ADD COLUMN IF NOT EXISTS date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE branches ADD COLUMN IF NOT EXISTS date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE bank_institution_codes ADD COLUMN IF NOT EXISTS date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE bank_institution_codes ADD COLUMN IF NOT EXISTS date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE holidays ADD COLUMN IF NOT EXISTS date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE holidays ADD COLUMN IF NOT EXISTS date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE language_locale ADD COLUMN IF NOT EXISTS date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE language_locale ADD COLUMN IF NOT EXISTS date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Update countries table
ALTER TABLE countries RENAME COLUMN id TO country_id;
ALTER TABLE countries ADD COLUMN IF NOT EXISTS region_lkp_id UUID;

-- Update currencies table
ALTER TABLE currencies RENAME COLUMN id TO currency_id;
ALTER TABLE currencies DROP COLUMN IF EXISTS decimal_precision;
ALTER TABLE currencies ADD COLUMN IF NOT EXISTS decimal_precision INTEGER DEFAULT 2;
ALTER TABLE currencies ADD COLUMN IF NOT EXISTS is_major BOOLEAN DEFAULT FALSE;

-- Update branches table
ALTER TABLE branches RENAME COLUMN id TO branch_id;
ALTER TABLE branches ADD COLUMN IF NOT EXISTS postal_code VARCHAR(20);
ALTER TABLE branches ADD COLUMN IF NOT EXISTS email VARCHAR(100);
ALTER TABLE branches ADD COLUMN IF NOT EXISTS division_id UUID;
ALTER TABLE branches ADD COLUMN IF NOT EXISTS branch_type_lkp_id UUID;
ALTER TABLE branches ADD COLUMN IF NOT EXISTS branch_manager_id UUID;
ALTER TABLE branches ADD COLUMN IF NOT EXISTS opening_hours VARCHAR(255);

-- Update bank_institution_codes table
ALTER TABLE bank_institution_codes RENAME COLUMN id TO institution_id;
ALTER TABLE bank_institution_codes ADD COLUMN IF NOT EXISTS iban_prefix VARCHAR(10);
ALTER TABLE bank_institution_codes ADD COLUMN IF NOT EXISTS institution_type_lkp_id UUID;

-- Update holidays table
ALTER TABLE holidays RENAME COLUMN id TO holiday_id;
ALTER TABLE holidays ADD COLUMN IF NOT EXISTS division_id UUID;
ALTER TABLE holidays ADD COLUMN IF NOT EXISTS local_name VARCHAR(100);
ALTER TABLE holidays DROP COLUMN IF EXISTS holiday_date;
ALTER TABLE holidays ADD COLUMN IF NOT EXISTS holiday_date DATE;
ALTER TABLE holidays ADD COLUMN IF NOT EXISTS recurrence_rule VARCHAR(255);
ALTER TABLE holidays DROP COLUMN IF EXISTS holiday_type;
ALTER TABLE holidays ADD COLUMN IF NOT EXISTS holiday_type_lkp_id UUID;
ALTER TABLE holidays ADD COLUMN IF NOT EXISTS business_closed BOOLEAN DEFAULT TRUE;
ALTER TABLE holidays ADD COLUMN IF NOT EXISTS bank_closed BOOLEAN DEFAULT TRUE;

-- Update language_locale table
ALTER TABLE language_locale RENAME COLUMN id TO locale_id;
ALTER TABLE language_locale ADD COLUMN IF NOT EXISTS country_code VARCHAR(10);
ALTER TABLE language_locale ADD COLUMN IF NOT EXISTS native_name VARCHAR(100);
ALTER TABLE language_locale ADD COLUMN IF NOT EXISTS rtl BOOLEAN DEFAULT FALSE;
ALTER TABLE language_locale ADD COLUMN IF NOT EXISTS sort_order INTEGER DEFAULT 0;

-- Create new tables for the new entities

-- 1) Administrative Division
CREATE TABLE IF NOT EXISTS administrative_division (
    division_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    country_id UUID REFERENCES countries(country_id) ON DELETE RESTRICT,
    code VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    level VARCHAR(50) NOT NULL,
    parent_division_id UUID REFERENCES administrative_division(division_id) ON DELETE RESTRICT,
    status status_enum NOT NULL,
    postal_code_pattern VARCHAR(255),
    time_zone VARCHAR(50),
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2) Lookup Domain
CREATE TABLE IF NOT EXISTS lookup_domain (
    domain_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    domain_code VARCHAR(50) NOT NULL,
    domain_name VARCHAR(100) NOT NULL,
    domain_desc TEXT,
    parent_domain_id UUID REFERENCES lookup_domain(domain_id) ON DELETE RESTRICT,
    multiselect_allowed BOOLEAN DEFAULT FALSE,
    hierarchy_allowed BOOLEAN DEFAULT FALSE,
    tenant_overridable BOOLEAN DEFAULT FALSE,
    extra_json JSONB,
    tenant_id UUID,
    status status_enum NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3) Lookup Item
CREATE TABLE IF NOT EXISTS lookup_item (
    item_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    domain_id UUID REFERENCES lookup_domain(domain_id) ON DELETE RESTRICT,
    item_code VARCHAR(50) NOT NULL,
    item_label_default VARCHAR(100) NOT NULL,
    item_desc TEXT,
    parent_item_id UUID REFERENCES lookup_item(item_id) ON DELETE RESTRICT,
    sort_order INTEGER DEFAULT 0,
    effective_from DATE DEFAULT '1900-01-01',
    effective_to DATE,
    is_current BOOLEAN DEFAULT TRUE,
    extra_json JSONB,
    tenant_id UUID,
    status status_enum NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4) Legal Form
CREATE TABLE IF NOT EXISTS legal_form (
    legal_form_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    country_id UUID REFERENCES countries(country_id) ON DELETE RESTRICT,
    code VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    is_corporate BOOLEAN DEFAULT TRUE,
    entity_type VARCHAR(50) NOT NULL,
    status status_enum NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5) Activity Code
CREATE TABLE IF NOT EXISTS activity_code (
    activity_code_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    country_id UUID REFERENCES countries(country_id) ON DELETE RESTRICT,
    code VARCHAR(20) NOT NULL,
    classification_sys VARCHAR(50) NOT NULL,
    description TEXT,
    parent_code_id UUID REFERENCES activity_code(activity_code_id) ON DELETE RESTRICT,
    high_risk BOOLEAN DEFAULT FALSE,
    risk_factors TEXT,
    status status_enum NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Add foreign key constraints for the new relationships
ALTER TABLE branches ADD CONSTRAINT fk_branches_division FOREIGN KEY (division_id) REFERENCES administrative_division(division_id) ON DELETE RESTRICT;
ALTER TABLE holidays ADD CONSTRAINT fk_holidays_division FOREIGN KEY (division_id) REFERENCES administrative_division(division_id) ON DELETE RESTRICT;

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_countries_iso_code ON countries(iso_code);
CREATE INDEX IF NOT EXISTS idx_currencies_iso_code ON currencies(iso_code);
CREATE INDEX IF NOT EXISTS idx_branches_country_id ON branches(country_id);
CREATE INDEX IF NOT EXISTS idx_branches_division_id ON branches(division_id);
CREATE INDEX IF NOT EXISTS idx_bank_institution_codes_country_id ON bank_institution_codes(country_id);
CREATE INDEX IF NOT EXISTS idx_holidays_country_id ON holidays(country_id);
CREATE INDEX IF NOT EXISTS idx_holidays_division_id ON holidays(division_id);
CREATE INDEX IF NOT EXISTS idx_language_locale_language_code ON language_locale(language_code);
CREATE INDEX IF NOT EXISTS idx_administrative_division_country_id ON administrative_division(country_id);
CREATE INDEX IF NOT EXISTS idx_administrative_division_parent_id ON administrative_division(parent_division_id);
CREATE INDEX IF NOT EXISTS idx_lookup_domain_code ON lookup_domain(domain_code);
CREATE INDEX IF NOT EXISTS idx_lookup_item_domain_id ON lookup_item(domain_id);
CREATE INDEX IF NOT EXISTS idx_lookup_item_code ON lookup_item(item_code);
CREATE INDEX IF NOT EXISTS idx_legal_form_country_id ON legal_form(country_id);
CREATE INDEX IF NOT EXISTS idx_activity_code_country_id ON activity_code(country_id);
CREATE INDEX IF NOT EXISTS idx_activity_code_parent_id ON activity_code(parent_code_id);