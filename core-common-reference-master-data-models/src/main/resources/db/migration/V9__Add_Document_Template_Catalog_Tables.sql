-- V9__Add_Document_Template_Catalog_Tables.sql
-- ---------------------------------------
-- This script adds tables for managing document templates with localization support.

-- Create the document_template_type_catalog table
CREATE TABLE IF NOT EXISTS document_template_type_catalog (
    type_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    type_code VARCHAR(50) NOT NULL,
    type_name VARCHAR(100) NOT NULL,
    description TEXT,
    status status_enum NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create index for better performance
CREATE INDEX IF NOT EXISTS idx_document_template_type_catalog_type_code ON document_template_type_catalog(type_code);

-- Add unique constraint to prevent duplicate type codes
ALTER TABLE document_template_type_catalog ADD CONSTRAINT uk_document_template_type_catalog_type_code UNIQUE (type_code);

-- Insert some default document template types
INSERT INTO document_template_type_catalog (type_code, type_name, description, status)
VALUES
('PDF', 'PDF Template', 'Document templates for PDF generation', 'ACTIVE'),
('DOCX', 'Word Template', 'Document templates for Microsoft Word documents', 'ACTIVE'),
('HTML', 'HTML Template', 'Document templates for HTML format', 'ACTIVE'),
('EXCEL', 'Excel Template', 'Document templates for Microsoft Excel spreadsheets', 'ACTIVE');

-- Create the document_template_catalog table
CREATE TABLE IF NOT EXISTS document_template_catalog (
    template_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    template_code VARCHAR(100) NOT NULL,
    type_id UUID REFERENCES document_template_type_catalog(type_id),
    category VARCHAR(100) NOT NULL,
    description TEXT,
    template_name VARCHAR(255) NOT NULL,
    template_content TEXT,
    template_variables JSONB,
    version VARCHAR(20),
    status status_enum NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_document_template_catalog_template_code ON document_template_catalog(template_code);
CREATE INDEX IF NOT EXISTS idx_document_template_catalog_category ON document_template_catalog(category);
CREATE INDEX IF NOT EXISTS idx_document_template_catalog_type_id ON document_template_catalog(type_id);

-- Add unique constraint to prevent duplicate template codes
ALTER TABLE document_template_catalog ADD CONSTRAINT uk_document_template_catalog_template_code UNIQUE (template_code);

-- Create the document_template_localization table for localized templates
CREATE TABLE IF NOT EXISTS document_template_localization (
    localization_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    template_id UUID NOT NULL REFERENCES document_template_catalog(template_id) ON DELETE CASCADE,
    locale_id UUID NOT NULL REFERENCES language_locale(locale_id) ON DELETE RESTRICT,
    template_name VARCHAR(255),
    template_content TEXT,
    status status_enum NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_document_template_localization_template_id ON document_template_localization(template_id);
CREATE INDEX IF NOT EXISTS idx_document_template_localization_locale_id ON document_template_localization(locale_id);

-- Add unique constraint to prevent duplicate localizations for the same template and locale
ALTER TABLE document_template_localization ADD CONSTRAINT uk_document_template_localization_template_locale UNIQUE (template_id, locale_id);
