-- V24__Insert_Contract_Document_Type_Values.sql

-- Flyway will run this script in a transaction by default, so no need for BEGIN/COMMIT here.

------------------------------------------------------------------------------
-- Insert values into contract_document_type table
------------------------------------------------------------------------------
INSERT INTO contract_document_type (document_code, name, description) VALUES 
('AGREEMENT', 'Agreement', 'General contract agreement documents'),
('TERMS_CONDITIONS', 'Terms and Conditions', 'Terms and conditions documents for contracts'),
('PRIVACY_POLICY', 'Privacy Policy', 'Privacy policy documents related to contracts'),
('DISCLOSURE', 'Disclosure', 'Disclosure statements and documents'),
('AMENDMENT', 'Amendment', 'Contract amendment and modification documents'),
('ADDENDUM', 'Addendum', 'Additional terms and addendum documents'),
('SCHEDULE', 'Schedule', 'Contract schedules and appendices'),
('EXHIBIT', 'Exhibit', 'Contract exhibits and attachments'),
('SIGNATURE_PAGE', 'Signature Page', 'Signature pages and execution documents'),
('CERTIFICATE', 'Certificate', 'Certificates and compliance documents'),
('NOTICE', 'Notice', 'Contract notices and communications'),
('WAIVER', 'Waiver', 'Waiver and release documents'),
('CONSENT', 'Consent', 'Consent forms and authorization documents'),
('POWER_OF_ATTORNEY', 'Power of Attorney', 'Power of attorney documents'),
('GUARANTY', 'Guaranty', 'Guaranty and surety documents');

-- End of V24__Insert_Contract_Document_Type_Values.sql
