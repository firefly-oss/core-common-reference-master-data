-- V13__Add_Transaction_Category_Localization_Table.sql
-- ---------------------------------------
-- This script adds a table for managing transaction category localizations.

-- Create the transaction_category_localization table
CREATE TABLE IF NOT EXISTS transaction_category_localization (
    localization_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    category_id UUID NOT NULL REFERENCES transaction_category_catalog(category_id) ON DELETE CASCADE,
    locale_id UUID NOT NULL REFERENCES language_locale(locale_id) ON DELETE RESTRICT,
    category_name VARCHAR(255),
    description TEXT,
    status status_enum NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_transaction_category_localization_category_id ON transaction_category_localization(category_id);
CREATE INDEX IF NOT EXISTS idx_transaction_category_localization_locale_id ON transaction_category_localization(locale_id);

-- Add unique constraint to prevent duplicate localizations for the same category and locale
ALTER TABLE transaction_category_localization ADD CONSTRAINT uk_transaction_category_localization_category_locale UNIQUE (category_id, locale_id);
