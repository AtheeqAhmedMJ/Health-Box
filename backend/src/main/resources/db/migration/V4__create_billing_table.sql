CREATE TABLE billing (
    id BIGSERIAL PRIMARY KEY,
    patient_phno VARCHAR(15) NOT NULL,
    appointment_id BIGINT NOT NULL,
    prescription_id BIGINT,

    consultation_fee DOUBLE PRECISION DEFAULT 0,
    medicine_charges DOUBLE PRECISION DEFAULT 0,
    lab_charges DOUBLE PRECISION DEFAULT 0,
    other_charges DOUBLE PRECISION DEFAULT 0,
    total_amount DOUBLE PRECISION DEFAULT 0,

    payment_status VARCHAR(20) DEFAULT 'PENDING',
    payment_mode VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_patient FOREIGN KEY (patient_phno)
        REFERENCES patients(phno)
        ON DELETE CASCADE,

    CONSTRAINT fk_appointment FOREIGN KEY (appointment_id)
        REFERENCES appointments(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_prescription FOREIGN KEY (prescription_id)
        REFERENCES prescriptions(id)
        ON DELETE SET NULL
);
