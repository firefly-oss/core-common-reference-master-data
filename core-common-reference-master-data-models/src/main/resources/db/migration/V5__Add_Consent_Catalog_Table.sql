-- V5__Add_Consent_Catalog_Table.sql
-- ---------------------------------------
-- This script adds the consent_catalog table for managing various types of consents that users can give.

-- Create the consent_catalog table
CREATE TABLE IF NOT EXISTS consent_catalog (
    consent_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    consent_type VARCHAR(50) NOT NULL,
    consent_description TEXT,
    expiry_period_days INTEGER,
    consent_version VARCHAR(20),
    consent_source VARCHAR(50),
    status status_enum NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_consent_catalog_consent_type ON consent_catalog(consent_type);