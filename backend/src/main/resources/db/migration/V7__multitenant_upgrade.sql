-- =====================================
-- ORGANIZATION (TENANT ROOT)
-- =====================================

CREATE TABLE IF NOT EXISTS organizations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO organizations(name)
SELECT 'Health Box Default'
WHERE NOT EXISTS (
    SELECT 1 FROM organizations
);

-- =====================================
-- CLINICS
-- =====================================

CREATE TABLE IF NOT EXISTS clinics (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL,
    name VARCHAR(255),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_org_clinic
    FOREIGN KEY (organization_id)
    REFERENCES organizations(id)
);

-- =====================================
-- DOCTORS
-- =====================================

CREATE TABLE IF NOT EXISTS doctors (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL,
    clinic_id BIGINT,
    name VARCHAR(255),
    specialization VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_org_doctor
    FOREIGN KEY (organization_id)
    REFERENCES organizations(id),

    CONSTRAINT fk_clinic_doctor
    FOREIGN KEY (clinic_id)
    REFERENCES clinics(id)
);

-- =====================================
-- ADD ORG COLUMN TO EXISTING TABLES
-- =====================================

ALTER TABLE patients ADD COLUMN IF NOT EXISTS organization_id BIGINT;
ALTER TABLE appointments ADD COLUMN IF NOT EXISTS organization_id BIGINT;
ALTER TABLE prescriptions ADD COLUMN IF NOT EXISTS organization_id BIGINT;
ALTER TABLE billing ADD COLUMN IF NOT EXISTS organization_id BIGINT;
ALTER TABLE users ADD COLUMN IF NOT EXISTS organization_id BIGINT;

-- default tenant
UPDATE patients SET organization_id = 1 WHERE organization_id IS NULL;
UPDATE appointments SET organization_id = 1 WHERE organization_id IS NULL;
UPDATE prescriptions SET organization_id = 1 WHERE organization_id IS NULL;
UPDATE billing SET organization_id = 1 WHERE organization_id IS NULL;
UPDATE users SET organization_id = 1 WHERE organization_id IS NULL;

ALTER TABLE patients ALTER COLUMN organization_id SET NOT NULL;
ALTER TABLE appointments ALTER COLUMN organization_id SET NOT NULL;
ALTER TABLE prescriptions ALTER COLUMN organization_id SET NOT NULL;
ALTER TABLE billing ALTER COLUMN organization_id SET NOT NULL;
ALTER TABLE users ALTER COLUMN organization_id SET NOT NULL;