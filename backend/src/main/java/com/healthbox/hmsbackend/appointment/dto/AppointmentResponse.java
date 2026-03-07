package com.healthbox.hmsbackend.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppointmentResponse {

    private Long id;
    private Integer queueNumber;
    private String status;
}