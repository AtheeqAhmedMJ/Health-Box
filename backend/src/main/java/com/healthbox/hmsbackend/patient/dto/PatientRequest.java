package com.healthbox.hmsbackend.patient.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientRequest {

    @NotBlank
    private String phno;

    @NotBlank
    private String name;

    private Integer age;
    private String gender;
    private Long clinicId;
}