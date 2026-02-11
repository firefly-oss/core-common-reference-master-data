-- V23__Add_Contract_Document_Type_Table.sql

-- Flyway will run this script in a transaction by default, so no need for BEGIN/COMMIT here.

------------------------------------------------------------------------------
-- Create TABLE contract_document_type
------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS contract_document_type (
    document_type_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    document_code       VARCHAR(50)     NOT NULL UNIQUE,
    name                VARCHAR(100)    NOT NULL,
    description         VARCHAR(500),
    is_active           BOOLEAN         NOT NULL DEFAULT TRUE,
    date_created        TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_updated        TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create index on document_code for faster lookups
CREATE INDEX IF NOT EXISTS idx_contract_document_type_code ON contract_document_type(document_code);

-- Create index on is_active for filtering active records
CREATE INDEX IF NOT EXISTS idx_contract_document_type_active ON contract_document_type(is_active);

-- End of V23__Add_Contract_Document_Type_Table.sql
