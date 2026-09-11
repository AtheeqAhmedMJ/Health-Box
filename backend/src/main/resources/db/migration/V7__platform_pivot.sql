-- Pivot: doctor IS the admin (drop separate DOCTOR/PHARMACIST roles), add SUPER_ADMIN,
-- add OTP/email, charge items, and the payment-gated consultation flow.

-- Roles: collapse DOCTOR/PHARMACIST into ADMIN, then swap the check constraint.
ALTER TABLE users DROP CONSTRAINT IF EXISTS role_check;
UPDATE users SET role = 'ADMIN' WHERE role IN ('DOCTOR', 'PHARMACIST');
ALTER TABLE users ADD CONSTRAINT role_check CHECK (role IN ('SUPER_ADMIN','ADMIN','PATIENT'));

-- SUPER_ADMIN has no hospital.
ALTER TABLE users ALTER COLUMN hospital_id DROP NOT NULL;

-- Email for OTP verification.
ALTER TABLE users ADD COLUMN email VARCHAR(150);
UPDATE users SET email = phno || '@placeholder.local' WHERE email IS NULL;
ALTER TABLE users ALTER COLUMN email SET NOT NULL;
ALTER TABLE users ADD CONSTRAINT uq_users_email UNIQUE (email);

-- Appointments are just scheduling requests — must be bookable before a paid
-- consultation ever creates a Patient profile, so the hard FK has to go.
ALTER TABLE appointments DROP CONSTRAINT IF EXISTS fk_patient;
ALTER TABLE appointments ADD COLUMN patient_name VARCHAR(150);

-- A consultation payment can happen without a pre-booked appointment (walk-ins),
-- so appointment_id must be optional on both downstream tables.
ALTER TABLE prescriptions ALTER COLUMN appointment_id DROP NOT NULL;
ALTER TABLE billing ALTER COLUMN appointment_id DROP NOT NULL;

-- Admin's configurable rate card.
CREATE TABLE charge_items (
    id BIGSERIAL PRIMARY KEY,
    hospital_id BIGINT NOT NULL REFERENCES hospitals(id),
    name VARCHAR(100) NOT NULL,
    amount_paise BIGINT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE INDEX idx_charge_items_hospital ON charge_items(hospital_id);

-- OTP tokens for email verification during self-registration.
CREATE TABLE otp_tokens (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(150) NOT NULL,
    otp_hash VARCHAR(200) NOT NULL,
    purpose VARCHAR(30) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    consumed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_otp_email_purpose ON otp_tokens(email, purpose);

-- Payment staging: Patient/Prescription/Billing rows are only written once
-- the linked payment_order flips to PAID (see ConsultationMaterializer).
CREATE TABLE payment_orders (
    id BIGSERIAL PRIMARY KEY,
    hospital_id BIGINT NOT NULL REFERENCES hospitals(id),
    doctor_phno VARCHAR(15) NOT NULL,
    patient_phno VARCHAR(15) NOT NULL,
    appointment_id BIGINT,
    razorpay_order_id VARCHAR(100) NOT NULL UNIQUE,
    razorpay_payment_id VARCHAR(100),
    amount_paise BIGINT NOT NULL,
    platform_fee_paise BIGINT NOT NULL,
    doctor_amount_paise BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'CREATED',
    payload JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    paid_at TIMESTAMP
);
CREATE INDEX idx_payment_orders_hospital ON payment_orders(hospital_id);
CREATE INDEX idx_payment_orders_patient ON payment_orders(patient_phno);
CREATE INDEX idx_payment_orders_status ON payment_orders(status);
