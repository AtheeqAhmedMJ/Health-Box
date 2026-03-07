package com.healthbox.hmsbackend.prescription.repository;

import com.healthbox.hmsbackend.prescription.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository
        extends JpaRepository<Prescription, Long> {

    List<Prescription> findByPatientPhno(String phno);

    List<Prescription> findByAppointmentId(Long appointmentId);
}