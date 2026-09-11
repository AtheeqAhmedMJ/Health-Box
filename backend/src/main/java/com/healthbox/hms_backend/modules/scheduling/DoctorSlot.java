package com.healthbox.hms_backend.modules.scheduling;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;

// Weekly recurring availability slot for a doctor (scheduling automation).
@Entity
@Table(name = "doctor_slots")
@Getter
@Setter
public class DoctorSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hospital_id", nullable = false)
    private Long hospitalId;

    @Column(name = "doctor_phno", nullable = false)
    private String doctorPhno;

    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek; // 0=Sunday .. 6=Saturday

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    private boolean active = true;
}
