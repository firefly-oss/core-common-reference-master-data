-- V17__Insert_Contract_Type_Values.sql

-- Flyway will run this script in a transaction by default, so no need for BEGIN/COMMIT here.

------------------------------------------------------------------------------
-- Insert values into contract_type table
------------------------------------------------------------------------------
INSERT INTO contract_type (contract_code, name, description) VALUES 
('ACCOUNT', 'Account', 'Banking account contracts such as checking, savings, etc.'),
('CARD', 'Card', 'Credit and debit card contracts'),
('LOAN', 'Loan', 'Personal and business loan contracts'),
('MORTGAGE', 'Mortgage', 'Real estate mortgage contracts'),
('INVESTMENT', 'Investment', 'Investment and securities contracts');

-- End of V17__Insert_Contract_Type_Values.sql