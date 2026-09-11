package com.healthbox.hms_backend.modules.charges;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChargeItemRepository extends JpaRepository<ChargeItem, Long> {
    List<ChargeItem> findByHospitalIdAndActiveTrue(Long hospitalId);
    List<ChargeItem> findByHospitalId(Long hospitalId);
}
