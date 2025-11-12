package com.healthbox.hms_backend.modules.appointments;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientPhno(String phno);

    boolean existsByPatientPhnoAndDate(String patientPhno, LocalDate date);
}
