package com.healthbox.hmsbackend.appointment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.*;

@Entity
@Table(name="appointments")
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long doctorId;
    private Long clinicId;

    private String patientName;
    private String patientPhno;

    private LocalDate date;
    private LocalTime startTime;

    // ========= QUEUE SYSTEM =========

    private Integer queueNumber;

    private Boolean isWalkIn = false;

    private LocalDateTime checkInTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status =
            AppointmentStatus.BOOKED;
}