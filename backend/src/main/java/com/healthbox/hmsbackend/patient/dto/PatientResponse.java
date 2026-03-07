package com.healthbox.hmsbackend.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatientResponse {

    private String phno;
    private String name;
    private Integer age;
    private String gender;
}