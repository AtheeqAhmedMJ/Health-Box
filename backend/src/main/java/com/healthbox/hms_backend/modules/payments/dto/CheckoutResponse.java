package com.healthbox.hms_backend.modules.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckoutResponse {
    private Long paymentOrderId;
    private String razorpayOrderId;
    private long amountPaise;
    private String currency;
    private String keyId; // public key, safe to expose to frontend checkout widget
}
