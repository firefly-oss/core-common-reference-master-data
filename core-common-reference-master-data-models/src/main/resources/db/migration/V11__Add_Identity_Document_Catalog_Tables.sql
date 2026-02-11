-- V11__Add_Identity_Document_Catalog_Tables.sql
-- ---------------------------------------
-- This script adds tables for managing identity document categories, types, and localization.

-- Create the identity_document_category_catalog table
CREATE TABLE IF NOT EXISTS identity_document_category_catalog (
    category_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    category_code VARCHAR(50) NOT NULL,
    category_name VARCHAR(100) NOT NULL,
    description TEXT,
    status status_enum NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_identity_document_category_catalog_category_code ON identity_document_category_catalog(category_code);

-- Add unique constraint to prevent duplicate category codes
ALTER TABLE identity_document_category_catalog ADD CONSTRAINT uk_identity_document_category_catalog_category_code UNIQUE (category_code);

-- Insert default identity document categories
INSERT INTO identity_document_category_catalog (category_code, category_name, description, status)
VALUES
('PERSONAL', 'Personal', 'Personal identification documents', 'ACTIVE'),
('BUSINESS', 'Business', 'Business/corporate identification documents', 'ACTIVE'),
('GOVERNMENT', 'Government', 'Government-issued identification documents', 'ACTIVE'),
('INTERNATIONAL', 'International', 'International identification documents', 'ACTIVE'),
('REGIONAL', 'Regional', 'Regional/local identification documents', 'ACTIVE'),
('OTHER', 'Other', 'Other types of identification documents', 'ACTIVE');

-- Create the identity_document_catalog table
CREATE TABLE IF NOT EXISTS identity_document_catalog (
    document_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    document_code VARCHAR(50) NOT NULL,
    document_name VARCHAR(100) NOT NULL,
    category_id UUID REFERENCES identity_document_category_catalog(category_id) ON DELETE RESTRICT,
    description TEXT,
    validation_regex VARCHAR(255),
    format_description VARCHAR(255),
    country_id UUID REFERENCES countries(country_id) ON DELETE RESTRICT,
    status status_enum NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_identity_document_catalog_document_code ON identity_document_catalog(document_code);
CREATE INDEX IF NOT EXISTS idx_identity_document_catalog_category_id ON identity_document_catalog(category_id);
CREATE INDEX IF NOT EXISTS idx_identity_document_catalog_country_id ON identity_document_catalog(country_id);

-- Add unique constraint to prevent duplicate document codes
ALTER TABLE identity_document_catalog ADD CONSTRAINT uk_identity_document_catalog_document_code UNIQUE (document_code);

-- Insert default identity documents
INSERT INTO identity_document_catalog (document_code, document_name, category_id, description, status)
VALUES
('PASSPORT', 'Passport', (SELECT category_id FROM identity_document_category_catalog WHERE category_code = 'INTERNATIONAL'), 'International passport for travel and identification', 'ACTIVE'),
('NATIONAL_ID', 'National ID Card', (SELECT category_id FROM identity_document_category_catalog WHERE category_code = 'GOVERNMENT'), 'Generic national identification card', 'ACTIVE'),
('DRIVER_LICENSE', 'Driver License', (SELECT category_id FROM identity_document_category_catalog WHERE category_code = 'GOVERNMENT'), 'License to operate motor vehicles', 'ACTIVE'),
('RESIDENCE_PERMIT', 'Residence Permit', (SELECT category_id FROM identity_document_category_catalog WHERE category_code = 'GOVERNMENT'), 'Document proving legal residence status', 'ACTIVE'),
('CITIZEN_CARD', 'Citizen Card', (SELECT category_id FROM identity_document_category_catalog WHERE category_code = 'GOVERNMENT'), 'National citizen identification card', 'ACTIVE'),
('MILITARY_ID', 'Military ID', (SELECT category_id FROM identity_document_category_catalog WHERE category_code = 'GOVERNMENT'), 'Military identification document', 'ACTIVE'),
('BIRTH_CERTIFICATE', 'Birth Certificate', (SELECT category_id FROM identity_document_category_catalog WHERE category_code = 'GOVERNMENT'), 'Official record of birth', 'ACTIVE'),
('TAX_ID', 'Tax ID', (SELECT category_id FROM identity_document_category_catalog WHERE category_code = 'GOVERNMENT'), 'Tax identification document', 'ACTIVE'),
('DNI', 'Documento Nacional de Identidad', (SELECT category_id FROM identity_document_category_catalog WHERE category_code = 'GOVERNMENT'), 'Spanish national identity document', 'ACTIVE'),
('NIE', 'Número de Identidad de Extranjero', (SELECT category_id FROM identity_document_category_catalog WHERE category_code = 'GOVERNMENT'), 'Spanish foreigner identity number', 'ACTIVE'),
('NIF', 'Número de Identificación Fiscal', (SELECT category_id FROM identity_document_category_catalog WHERE category_code = 'GOVERNMENT'), 'Spanish fiscal identification number for individuals', 'ACTIVE'),
('CIF', 'Código de Identificación Fiscal', (SELECT category_id FROM identity_document_category_catalog WHERE category_code = 'BUSINESS'), 'Spanish fiscal identification code for companies', 'ACTIVE'),
('VAT_CERTIFICATE', 'VAT Certificate', (SELECT category_id FROM identity_document_category_catalog WHERE category_code = 'BUSINESS'), 'Value Added Tax registration certificate', 'ACTIVE'),
('OTHER', 'Other Identity Document', (SELECT category_id FROM identity_document_category_catalog WHERE category_code = 'OTHER'), 'Other types of identity documents not listed', 'ACTIVE');



-- Create the identity_document_localization table for localized document information
CREATE TABLE IF NOT EXISTS identity_document_localization (
    localization_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    document_id UUID NOT NULL REFERENCES identity_document_catalog(document_id) ON DELETE CASCADE,
    locale_id UUID NOT NULL REFERENCES language_locale(locale_id) ON DELETE RESTRICT,
    document_name VARCHAR(255),
    description TEXT,
    format_description TEXT,
    status status_enum NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_identity_document_localization_document_id ON identity_document_localization(document_id);
CREATE INDEX IF NOT EXISTS idx_identity_document_localization_locale_id ON identity_document_localization(locale_id);

-- Add unique constraint to prevent duplicate localizations for the same document and locale
ALTER TABLE identity_document_localization ADD CONSTRAINT uk_identity_document_localization_document_locale UNIQUE (document_id, locale_id);
