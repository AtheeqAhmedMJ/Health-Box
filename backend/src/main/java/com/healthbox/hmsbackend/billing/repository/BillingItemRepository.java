package com.healthbox.hmsbackend.billing.repository;

import com.healthbox.hmsbackend.billing.entity.BillingItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillingItemRepository
        extends JpaRepository<BillingItem, Long> {

    List<BillingItem> findByBillingId(Long billingId);
}