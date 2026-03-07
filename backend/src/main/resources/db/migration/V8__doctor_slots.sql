CREATE TABLE doctor_slots (

    id BIGSERIAL PRIMARY KEY,

    organization_id BIGINT NOT NULL,
    clinic_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,

    day_of_week VARCHAR(20) NOT NULL,

    start_time TIME NOT NULL,
    end_time TIME NOT NULL,

    slot_duration_minutes INT NOT NULL,

    max_patients INT DEFAULT 0,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_slot_org
        FOREIGN KEY (organization_id)
        REFERENCES organizations(id),

    CONSTRAINT fk_slot_clinic
        FOREIGN KEY (clinic_id)
        REFERENCES clinics(id),

    CONSTRAINT fk_slot_doctor
        FOREIGN KEY (doctor_id)
        REFERENCES doctors(id)
);