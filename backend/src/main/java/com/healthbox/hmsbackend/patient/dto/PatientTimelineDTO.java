package com.healthbox.hmsbackend.patient.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientTimelineDTO {

    private Long appointmentId;
    private LocalDate date;
    private Long doctorId;
    private String status;

    private Object prescription;
    private Object billing;
}