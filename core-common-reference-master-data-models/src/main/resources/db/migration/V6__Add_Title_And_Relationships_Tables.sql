-- V6__Add_Title_And_Relationships_Tables.sql

-- Flyway will run this script in a transaction by default, so no need for BEGIN/COMMIT here.

------------------------------------------------------------------------------
-- Create TABLE title_master
------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS title_master (
                                            title_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                            title_prefix       VARCHAR(50),       -- e.g., 'MR', 'MRS', 'DR'
    title_description  VARCHAR(200),      -- e.g., 'Mr.', 'Mrs.', 'Doctor'
    status             status_enum,
    date_created       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_updated       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

------------------------------------------------------------------------------
-- Create TABLE relationship_type_master
------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS relationship_type_master (
                                                        relationship_type_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                                        relationship_type_code          VARCHAR(100),       -- e.g., 'BENEFICIARY', 'CEO'
    relationship_type_description   VARCHAR(200),       -- e.g., 'Beneficiary', 'Chief Executive Officer'
    status                          status_enum,
    date_created                    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_updated                    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

-- End of V6__Add_Title_And_Relationships_Tables.sql
