package com.healthbox.hmsbackend.billing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorTreatmentRequest {

    private Long doctorId;
    private Long treatmentId;
    private Double price;
}