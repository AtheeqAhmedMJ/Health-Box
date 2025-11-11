CREATE TABLE appointments (
    id BIGSERIAL PRIMARY KEY,
    patient_phno VARCHAR(15) NOT NULL,
    date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_patient FOREIGN KEY (patient_phno)
        REFERENCES patients(phno)
        ON DELETE CASCADE
);
