-- V26__Extend_Consent_Catalog_For_Experience_Layer.sql
-- ----------------------------------------------------
-- Adds the fields required by the experience-tier consents endpoint so that
-- consents can be presented with display ordering, mandatory/optional state
-- and product-scope filtering. Existing consent_description (TEXT) is reused
-- as the user-facing label (HTML allowed); existing consent_type carries the
-- catalog code already.
--
-- Backwards compatible: every new column is nullable or has a safe default;
-- existing rows are not modified.

ALTER TABLE consent_catalog
    ADD COLUMN IF NOT EXISTS is_required BOOLEAN NOT NULL DEFAULT false;

ALTER TABLE consent_catalog
    ADD COLUMN IF NOT EXISTS sort_order INTEGER NOT NULL DEFAULT 0;

-- Optional product scope. NULL means the consent applies to every product.
ALTER TABLE consent_catalog
    ADD COLUMN IF NOT EXISTS applicable_product VARCHAR(50);

CREATE INDEX IF NOT EXISTS idx_consent_catalog_applicable_product
    ON consent_catalog(applicable_product);
