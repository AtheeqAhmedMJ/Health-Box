-- =====================================
-- ORGANIZATION (TENANT)
-- =====================================

CREATE TABLE organizations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO organizations(name)
VALUES ('Health Box Default');
-- =====================================
-- CLINICS
-- =====================================

CREATE TABLE clinics (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    address TEXT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_org_clinic
        FOREIGN KEY (organization_id)
        REFERENCES organizations(id)
        ON DELETE CASCADE
);
-- =====================================
-- DOCTORS
-- =====================================

CREATE TABLE doctors (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL,
    clinic_id BIGINT NOT NULL,

    name VARCHAR(255) NOT NULL,
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
-- MULTI TENANT COLUMNS
-- =====================================

ALTER TABLE patients
ADD COLUMN organization_id BIGINT;

ALTER TABLE appointments
ADD COLUMN organization_id BIGINT;

ALTER TABLE prescriptions
ADD COLUMN organization_id BIGINT;

ALTER TABLE billing
ADD COLUMN organization_id BIGINT;

ALTER TABLE users
ADD COLUMN organization_id BIGINT;
ALTER TABLE appointments
ADD COLUMN clinic_id BIGINT;

ALTER TABLE appointments
ADD COLUMN doctor_id BIGINT;

ALTER TABLE patients
ADD CONSTRAINT fk_patient_org
FOREIGN KEY (organization_id)
REFERENCES organizations(id);

ALTER TABLE appointments
ADD CONSTRAINT fk_appt_org
FOREIGN KEY (organization_id)
REFERENCES organizations(id);

ALTER TABLE prescriptions
ADD CONSTRAINT fk_pres_org
FOREIGN KEY (organization_id)
REFERENCES organizations(id);

ALTER TABLE billing
ADD CONSTRAINT fk_bill_org
FOREIGN KEY (organization_id)
REFERENCES organizations(id);

ALTER TABLE users
ADD CONSTRAINT fk_user_org
FOREIGN KEY (organization_id)
REFERENCES organizations(id);

ALTER TABLE appointments
ADD CONSTRAINT fk_appt_clinic
FOREIGN KEY (clinic_id)
REFERENCES clinics(id);

ALTER TABLE appointments
ADD CONSTRAINT fk_appt_doctor
FOREIGN KEY (doctor_id)
REFERENCES doctors(id);
UPDATE patients SET organization_id = 1;
UPDATE appointments SET organization_id = 1;
UPDATE prescriptions SET organization_id = 1;
UPDATE billing SET organization_id = 1;
UPDATE users SET organization_id = 1;

ALTER TABLE patients ALTER COLUMN organization_id SET NOT NULL;
ALTER TABLE appointments ALTER COLUMN organization_id SET NOT NULL;
ALTER TABLE prescriptions ALTER COLUMN organization_id SET NOT NULL;
ALTER TABLE billing ALTER COLUMN organization_id SET NOT NULL;
ALTER TABLE users ALTER COLUMN organization_id SET NOT NULL;
UPDATE clinics SET organization_id = 1;
UPDATE doctors SET organization_id = 1;
ALTER TABLE clinics ALTER COLUMN organization_id SET NOT NULL;
ALTER TABLE doctors ALTER COLUMN organization_id SET NOT NULL;