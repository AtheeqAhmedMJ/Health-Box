package com.healthbox.hmsbackend.appointment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentRequest {

    private Long clinicId;
    private Long doctorId;

    private String patientName;
    private String patientPhno;

    private String date;
    private String startTime;
    private String endTime;
}