package com.healthbox.hmsbackend.billing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BillingResponse {

    private Long billingId;
    private Double totalAmount;
    private String paymentStatus;
}