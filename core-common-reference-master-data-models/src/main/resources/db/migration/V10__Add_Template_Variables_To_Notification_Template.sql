-- V10__Add_Template_Variables_To_Notification_Template.sql
-- ---------------------------------------
-- This script adds template variables support to notification message templates.

-- Add template_variables column to notification_message_template table
ALTER TABLE notification_message_template
ADD COLUMN IF NOT EXISTS template_variables JSONB;
