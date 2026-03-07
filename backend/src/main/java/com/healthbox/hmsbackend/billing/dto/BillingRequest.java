package com.healthbox.hmsbackend.billing.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BillingRequest {

    private Long clinicId;
    private Long doctorId;
    private String patientPhno;
    private Long appointmentId;

    private List<Long> treatmentIds;
}