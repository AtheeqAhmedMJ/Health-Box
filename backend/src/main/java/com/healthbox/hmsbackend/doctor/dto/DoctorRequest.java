package com.healthbox.hmsbackend.doctor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorRequest {

    @NotBlank
    private String name;

    private String specialization;
    private String phno;
    private String email;
}