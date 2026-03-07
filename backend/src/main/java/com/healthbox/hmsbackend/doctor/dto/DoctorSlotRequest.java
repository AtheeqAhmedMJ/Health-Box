package com.healthbox.hmsbackend.doctor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorSlotRequest {

    private Long clinicId;
    private Long doctorId;

    private String dayOfWeek;

    private String startTime;
    private String endTime;

    private Integer slotDurationMinutes;
    private Integer maxPatients;
}