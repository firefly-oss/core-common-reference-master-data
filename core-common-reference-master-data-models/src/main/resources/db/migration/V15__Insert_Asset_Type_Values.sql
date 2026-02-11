-- V15__Insert_Asset_Type_Values.sql

-- Flyway will run this script in a transaction by default, so no need for BEGIN/COMMIT here.

------------------------------------------------------------------------------
-- Insert values into asset_type table
------------------------------------------------------------------------------
INSERT INTO asset_type (asset_code, name, description) VALUES 
('REAL_ESTATE', 'Real Estate', 'Property consisting of land and buildings'),
('VEHICLE', 'Vehicle', 'Means of transportation such as cars, trucks, boats, etc.'),
('EQUIPMENT', 'Equipment', 'Machinery, tools, and other durable goods'),
('FINANCIAL', 'Financial', 'Financial assets such as stocks, bonds, and cash'),
('OTHER', 'Other', 'Other types of assets not categorized elsewhere');

-- End of V15__Insert_Asset_Type_Values.sql