package com.healthbox.hmsbackend.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DoctorResponse {

    private Long id;
    private String name;
    private String specialization;
}