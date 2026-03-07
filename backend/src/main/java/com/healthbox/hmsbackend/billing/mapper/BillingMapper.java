package com.healthbox.hmsbackend.billing.mapper;

import com.healthbox.hmsbackend.billing.dto.BillingResponse;
import com.healthbox.hmsbackend.billing.entity.Billing;
import org.springframework.stereotype.Component;

@Component
public class BillingMapper {

    public BillingResponse toResponse(Billing billing){

        return new BillingResponse(
                billing.getId(),
                billing.getTotalAmount(),
                billing.getPaymentStatus()
        );
    }
}