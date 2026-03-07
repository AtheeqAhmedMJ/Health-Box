package com.healthbox.hmsbackend.patient.repository;

import com.healthbox.hmsbackend.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository
        extends JpaRepository<Patient, String> {
}