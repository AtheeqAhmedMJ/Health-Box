package com.healthbox.hmsbackend.clinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClinicResponse {

    private Long id;
    private String name;
    private String address;
    private String phone;
}