package com.healthbox.hms_backend.modules.patients;

import org.springframework.data.jpa.repository.JpaRepository;
import com.healthbox.hms_backend.modules.patients.Patient;

public interface PatientRepository extends JpaRepository<Patient, String> {
}
