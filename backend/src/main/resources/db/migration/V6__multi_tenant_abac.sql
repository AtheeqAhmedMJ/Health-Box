CREATE TABLE hospitals (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    code VARCHAR(30) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- seed a default tenant so existing dev data (if any) has somewhere to attach
INSERT INTO hospitals (name, code) VALUES ('Default Hospital', 'DEFAULT01');

ALTER TABLE users ADD COLUMN hospital_id BIGINT REFERENCES hospitals(id);
UPDATE users SET hospital_id = (SELECT id FROM hospitals WHERE code = 'DEFAULT01') WHERE hospital_id IS NULL;
ALTER TABLE users ALTER COLUMN hospital_id SET NOT NULL;
ALTER TABLE users ADD CONSTRAINT role_check CHECK (role IN ('ADMIN','DOCTOR','PHARMACIST','PATIENT'));

ALTER TABLE patients ADD COLUMN hospital_id BIGINT REFERENCES hospitals(id);
ALTER TABLE patients ADD COLUMN assigned_doctor_phno VARCHAR(15);
UPDATE patients SET hospital_id = (SELECT id FROM hospitals WHERE code = 'DEFAULT01') WHERE hospital_id IS NULL;
ALTER TABLE patients ALTER COLUMN hospital_id SET NOT NULL;
CREATE INDEX idx_patients_hospital ON patients(hospital_id);
CREATE INDEX idx_patients_doctor ON patients(assigned_doctor_phno);

ALTER TABLE appointments ADD COLUMN hospital_id BIGINT REFERENCES hospitals(id);
ALTER TABLE appointments ADD COLUMN doctor_phno VARCHAR(15);
ALTER TABLE appointments ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED';
UPDATE appointments SET hospital_id = (SELECT id FROM hospitals WHERE code = 'DEFAULT01') WHERE hospital_id IS NULL;
ALTER TABLE appointments ALTER COLUMN hospital_id SET NOT NULL;
CREATE INDEX idx_appointments_hospital ON appointments(hospital_id);
CREATE INDEX idx_appointments_doctor ON appointments(doctor_phno);

ALTER TABLE prescriptions ADD COLUMN hospital_id BIGINT REFERENCES hospitals(id);
ALTER TABLE prescriptions ADD COLUMN doctor_phno VARCHAR(15);
UPDATE prescriptions SET hospital_id = (SELECT id FROM hospitals WHERE code = 'DEFAULT01') WHERE hospital_id IS NULL;
ALTER TABLE prescriptions ALTER COLUMN hospital_id SET NOT NULL;
CREATE INDEX idx_prescriptions_hospital ON prescriptions(hospital_id);
CREATE INDEX idx_prescriptions_doctor ON prescriptions(doctor_phno);

ALTER TABLE billing ADD COLUMN hospital_id BIGINT REFERENCES hospitals(id);
UPDATE billing SET hospital_id = (SELECT id FROM hospitals WHERE code = 'DEFAULT01') WHERE hospital_id IS NULL;
ALTER TABLE billing ALTER COLUMN hospital_id SET NOT NULL;
CREATE INDEX idx_billing_hospital ON billing(hospital_id);

CREATE TABLE pharmacy_records (
    id BIGSERIAL PRIMARY KEY,
    prescription_id BIGINT NOT NULL REFERENCES prescriptions(id) ON DELETE CASCADE,
    patient_phno VARCHAR(15) NOT NULL,
    hospital_id BIGINT NOT NULL REFERENCES hospitals(id),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    dispensed_by VARCHAR(15),
    notes VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_pharmacy_hospital ON pharmacy_records(hospital_id);

CREATE TABLE doctor_slots (
    id BIGSERIAL PRIMARY KEY,
    hospital_id BIGINT NOT NULL REFERENCES hospitals(id),
    doctor_phno VARCHAR(15) NOT NULL,
    day_of_week INT NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE INDEX idx_slots_hospital_doctor ON doctor_slots(hospital_id, doctor_phno);
