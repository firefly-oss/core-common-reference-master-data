-- V22__Add_Rule_Operation_Type_Table.sql

-- Flyway will run this script in a transaction by default, so no need for BEGIN/COMMIT here.

------------------------------------------------------------------------------
-- Create TABLE rule_operation_type
------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS rule_operation_type (
    operation_type_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    operation_type_code   VARCHAR(50)     NOT NULL,
    name                  VARCHAR(100)    NOT NULL,
    description           VARCHAR(200),
    is_active             BOOLEAN         NOT NULL DEFAULT TRUE,
    date_created          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_updated          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- End of V22__Add_Rule_Operation_Type_Table.sql
