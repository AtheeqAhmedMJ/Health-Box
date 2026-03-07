package com.healthbox.hmsbackend.billing.repository;

import com.healthbox.hmsbackend.billing.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentRepository
        extends JpaRepository<Treatment,Long> {
}