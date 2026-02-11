-- V12__Add_Transaction_Category_Catalog_Table.sql
-- ---------------------------------------
-- This script adds a table for managing transaction categories as a catalog.
-- It supports hierarchical structure with parent-child relationships.

-- Create the transaction_category_catalog table
CREATE TABLE IF NOT EXISTS transaction_category_catalog (
    category_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    category_code VARCHAR(50) NOT NULL,
    category_name VARCHAR(100) NOT NULL,
    description TEXT,
    parent_category_id UUID REFERENCES transaction_category_catalog(category_id) ON DELETE RESTRICT,
    status status_enum NOT NULL,
    svg_icon TEXT,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_transaction_category_catalog_category_code ON transaction_category_catalog(category_code);
CREATE INDEX IF NOT EXISTS idx_transaction_category_catalog_parent_id ON transaction_category_catalog(parent_category_id);

-- Add unique constraint to prevent duplicate category codes
ALTER TABLE transaction_category_catalog ADD CONSTRAINT uk_transaction_category_catalog_category_code UNIQUE (category_code);
