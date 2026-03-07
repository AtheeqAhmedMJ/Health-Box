package com.healthbox.hmsbackend.doctor.repository;

import com.healthbox.hmsbackend.doctor.entity.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface DoctorAvailabilityRepository
        extends JpaRepository<DoctorAvailability,Long> {

    List<DoctorAvailability> findByClinicId(Long clinicId);

    List<DoctorAvailability> findByDoctorIdAndDayOfWeek(
            Long doctorId,
            DayOfWeek day
    );
}