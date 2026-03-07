package com.healthbox.hmsbackend.doctor.repository;

import com.healthbox.hmsbackend.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository
        extends JpaRepository<Doctor,Long> {
}