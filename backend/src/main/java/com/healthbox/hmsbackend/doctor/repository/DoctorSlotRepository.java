package com.healthbox.hmsbackend.doctor.repository;

import com.healthbox.hmsbackend.doctor.entity.DoctorSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorSlotRepository
        extends JpaRepository<DoctorSlot, Long> {

    Optional<DoctorSlot> findByDoctorIdAndClinicId(
            Long doctorId,
            Long clinicId
    );
}