package com.healthbox.hmsbackend.billing.repository;

import com.healthbox.hmsbackend.billing.entity.DoctorTreatment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorTreatmentRepository
        extends JpaRepository<DoctorTreatment,Long> {

    Optional<DoctorTreatment>
    findByDoctorIdAndTreatmentId(
            Long doctorId,
            Long treatmentId
    );
}