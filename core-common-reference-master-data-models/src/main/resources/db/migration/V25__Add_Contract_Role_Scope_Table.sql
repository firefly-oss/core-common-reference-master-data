-- V25__Add_Contract_Role_Scope_Table.sql

-- Flyway will run this script in a transaction by default, so no need for BEGIN/COMMIT here.

------------------------------------------------------------------------------
-- Create TABLE contract_role_scope
------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS contract_role_scope (
    scope_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role_id UUID NOT NULL REFERENCES contract_role(role_id) ON DELETE CASCADE,
    scope_code VARCHAR(100) NOT NULL,
    scope_name VARCHAR(200) NOT NULL,
    description VARCHAR(500),
    action_type VARCHAR(50) NOT NULL, -- e.g., 'READ', 'WRITE', 'DELETE', 'EXECUTE'
    resource_type VARCHAR(100), -- e.g., 'DOCUMENT', 'TRANSACTION', 'ACCOUNT'
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_contract_role_scope_role_id ON contract_role_scope(role_id);
CREATE INDEX IF NOT EXISTS idx_contract_role_scope_scope_code ON contract_role_scope(scope_code);
CREATE INDEX IF NOT EXISTS idx_contract_role_scope_action_type ON contract_role_scope(action_type);
CREATE INDEX IF NOT EXISTS idx_contract_role_scope_resource_type ON contract_role_scope(resource_type);
CREATE INDEX IF NOT EXISTS idx_contract_role_scope_is_active ON contract_role_scope(is_active);

-- Create unique constraint to prevent duplicate scope codes per role
CREATE UNIQUE INDEX IF NOT EXISTS idx_contract_role_scope_unique_role_scope 
ON contract_role_scope(role_id, scope_code);

-- End of V25__Add_Contract_Role_Scope_Table.sql
