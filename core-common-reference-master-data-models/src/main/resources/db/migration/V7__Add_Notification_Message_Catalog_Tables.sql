-- V7__Add_Notification_Message_Catalog_Tables.sql
-- ---------------------------------------
-- This script adds tables for managing customer notification messages based on events.
-- It includes support for localization and HTML templates.

-- Create the notification_message_catalog table
CREATE TABLE IF NOT EXISTS notification_message_catalog (
    message_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    message_code VARCHAR(100) NOT NULL,
    message_type VARCHAR(50) NOT NULL,
    event_type VARCHAR(100) NOT NULL,
    description TEXT,
    default_subject VARCHAR(255),
    default_message TEXT,
    parameters JSONB,
    status status_enum NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the notification_message_template table for HTML templates
CREATE TABLE IF NOT EXISTS notification_message_template (
    template_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    message_id UUID NOT NULL REFERENCES notification_message_catalog(message_id) ON DELETE CASCADE,
    template_name VARCHAR(100) NOT NULL,
    template_content TEXT NOT NULL,
    template_type VARCHAR(50) NOT NULL,
    version VARCHAR(20),
    status status_enum NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the notification_message_localization table for localized messages
CREATE TABLE IF NOT EXISTS notification_message_localization (
    localization_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    message_id UUID NOT NULL REFERENCES notification_message_catalog(message_id) ON DELETE CASCADE,
    locale_id UUID NOT NULL REFERENCES language_locale(locale_id) ON DELETE RESTRICT,
    subject VARCHAR(255),
    message TEXT,
    status status_enum NOT NULL,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_notification_message_catalog_message_code ON notification_message_catalog(message_code);
CREATE INDEX IF NOT EXISTS idx_notification_message_catalog_event_type ON notification_message_catalog(event_type);
CREATE INDEX IF NOT EXISTS idx_notification_message_template_message_id ON notification_message_template(message_id);
CREATE INDEX IF NOT EXISTS idx_notification_message_localization_message_id ON notification_message_localization(message_id);
CREATE INDEX IF NOT EXISTS idx_notification_message_localization_locale_id ON notification_message_localization(locale_id);

-- Add unique constraint to prevent duplicate message codes
ALTER TABLE notification_message_catalog ADD CONSTRAINT uk_notification_message_catalog_message_code UNIQUE (message_code);

-- Add unique constraint to prevent duplicate templates for the same message
ALTER TABLE notification_message_template ADD CONSTRAINT uk_notification_message_template_message_template UNIQUE (message_id, template_name);

-- Add unique constraint to prevent duplicate localizations for the same message and locale
ALTER TABLE notification_message_localization ADD CONSTRAINT uk_notification_message_localization_message_locale UNIQUE (message_id, locale_id);
