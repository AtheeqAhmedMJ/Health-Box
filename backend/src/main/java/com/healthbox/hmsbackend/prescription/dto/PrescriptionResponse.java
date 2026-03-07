package com.healthbox.hmsbackend.prescription.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PrescriptionResponse {

    private Long id;
    private String patientPhno;
    private LocalDate date;
}