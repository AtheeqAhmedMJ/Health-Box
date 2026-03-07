package com.healthbox.hmsbackend.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DoctorDashboardResponse {

    private Object currentPatient;
    private Object nextPatient;

    private int waitingCount;
    private int completedToday;
    private int remainingToday;
}