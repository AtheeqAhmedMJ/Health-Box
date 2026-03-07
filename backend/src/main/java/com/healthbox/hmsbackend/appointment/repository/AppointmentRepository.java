package com.healthbox.hmsbackend.appointment.repository;

import com.healthbox.hmsbackend.appointment.entity.Appointment;
import com.healthbox.hmsbackend.appointment.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import com.healthbox.hmsbackend.appointment.entity.AppointmentStatus;

import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    // =====================================================
    // DOCTOR DASHBOARD
    // =====================================================
    List<Appointment> findByDoctorIdAndDate(
            Long doctorId,
            LocalDate date
    );

    // =====================================================
    // PATIENT TIMELINE
    // =====================================================
    List<Appointment> findByPatientPhno(String patientPhno);

    // =====================================================
    // SLOT LOCK SYSTEM
    // =====================================================
    boolean existsByDoctorIdAndDateAndStartTime(
            Long doctorId,
            LocalDate date,
            LocalTime startTime
    );

    // =====================================================
    // BOOKED SLOT LIST (Schedule Engine)
    // =====================================================
    @Query("""
        SELECT a.startTime
        FROM Appointment a
        WHERE a.doctorId = :doctorId
        AND a.date = :date
        AND a.status <> 'CANCELLED'
    """)
    List<LocalTime> findBookedSlots(
            @Param("doctorId") Long doctorId,
            @Param("date") LocalDate date
    );

    // =====================================================
    // QUEUE ENGINE
    // =====================================================
    @Query("""
        SELECT COALESCE(MAX(a.queueNumber),0)
        FROM Appointment a
        WHERE a.doctorId = :doctorId
        AND a.date = :date
    """)
    Integer getLastQueueNumber(
            @Param("doctorId") Long doctorId,
            @Param("date") LocalDate date
    );
    // next waiting patient
@Query("""
SELECT a FROM Appointment a
WHERE a.doctorId = :doctorId
AND a.date = :date
AND a.status = 'CHECKED_IN'
ORDER BY a.queueNumber ASC
LIMIT 1
""")
Appointment findNextPatient(
        @Param("doctorId") Long doctorId,
        @Param("date") LocalDate date
);

// current consulting
Appointment findFirstByDoctorIdAndDateAndStatus(
        Long doctorId,
        LocalDate date,
        AppointmentStatus status
);

    // =====================================================
    // ACTIVE QUEUE
    // =====================================================
    @Query("""
        SELECT a FROM Appointment a
        WHERE a.doctorId = :doctorId
        AND a.date = :date
        AND a.status IN (
            com.healthbox.hmsbackend.appointment.entity.AppointmentStatus.CHECKED_IN,
            com.healthbox.hmsbackend.appointment.entity.AppointmentStatus.CONSULTING
        )
        ORDER BY a.queueNumber
    """)
    List<Appointment> getActiveQueue(
            Long doctorId,
            LocalDate date
    );
}