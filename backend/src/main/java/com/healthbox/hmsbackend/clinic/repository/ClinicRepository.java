package com.healthbox.hmsbackend.clinic.repository;

import com.healthbox.hmsbackend.clinic.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository
        extends JpaRepository<Clinic, Long> {
}