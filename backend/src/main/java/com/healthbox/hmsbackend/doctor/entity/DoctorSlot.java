package com.healthbox.hmsbackend.doctor.entity;

import com.healthbox.hmsbackend.common.entity.BaseTenantEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "doctor_slots")
@Getter
@Setter
public class DoctorSlot extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clinicId;

    private Long doctorId;

    private String dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer slotDurationMinutes;

    private Integer maxPatients;
}