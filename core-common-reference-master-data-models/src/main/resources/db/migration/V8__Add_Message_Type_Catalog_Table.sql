-- V8__Add_Message_Type_Catalog_Table.sql
-- ---------------------------------------
-- This script adds a table for managing message types as a catalog.

-- Create the message_type_catalog table
CREATE TABLE IF NOT EXISTS message_type_catalog (
    type_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    type_code VARCHAR(50) NOT NULL,
    type_name VARCHAR(100) NOT NULL,
    description TEXT,
    status status_enum NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create index for better performance
CREATE INDEX IF NOT EXISTS idx_message_type_catalog_type_code ON message_type_catalog(type_code);

-- Add unique constraint to prevent duplicate type codes
ALTER TABLE message_type_catalog ADD CONSTRAINT uk_message_type_catalog_type_code UNIQUE (type_code);

-- Insert some default message types
INSERT INTO message_type_catalog (type_code, type_name, description, status)
VALUES 
('EMAIL', 'Email Message', 'Notification messages sent via email', 'ACTIVE'),
('SMS', 'SMS Message', 'Notification messages sent via SMS', 'ACTIVE'),
('PUSH', 'Push Notification', 'Notification messages sent as mobile push notifications', 'ACTIVE'),
('IN_APP', 'In-App Notification', 'Notification messages displayed within the application', 'ACTIVE');

-- Alter notification_message_catalog table to reference message_type_catalog
ALTER TABLE notification_message_catalog 
DROP COLUMN IF EXISTS message_type;

ALTER TABLE notification_message_catalog 
ADD COLUMN type_id UUID REFERENCES message_type_catalog(type_id);
