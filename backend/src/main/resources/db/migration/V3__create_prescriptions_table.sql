CREATE TABLE prescriptions (
    id BIGSERIAL PRIMARY KEY,
    patient_phno VARCHAR(15) NOT NULL,
    appointment_id BIGINT NOT NULL,
    date DATE NOT NULL,
    symptoms TEXT,
    bp VARCHAR(10),
    spo2 VARCHAR(10),
    grbs VARCHAR(10),
    temp VARCHAR(10),
    medicines JSONB,
    remarks TEXT,
    billing JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_patient FOREIGN KEY (patient_phno)
        REFERENCES patients(phno)
        ON DELETE CASCADE,

    CONSTRAINT fk_appointment FOREIGN KEY (appointment_id)
        REFERENCES appointments(id)
        ON DELETE CASCADE
);
