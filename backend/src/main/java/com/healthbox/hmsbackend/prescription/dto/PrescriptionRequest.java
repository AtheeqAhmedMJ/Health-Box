package com.healthbox.hmsbackend.prescription.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PrescriptionRequest {

    private String patientPhno;
    private Long appointmentId;
    private Long clinicId;

    private String symptoms;
    private String bp;
    private String spo2;
    private String temp;

    private List<Map<String,Object>> medicines;

    private String remarks;
}