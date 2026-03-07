package com.healthbox.hmsbackend.clinic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicRequest {

    @NotBlank
    private String name;

    private String address;

    private String phone;
}