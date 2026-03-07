package com.healthbox.hmsbackend.billing.repository;

import com.healthbox.hmsbackend.billing.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillingRepository
        extends JpaRepository<Billing, Long> {


    boolean existsByAppointmentId(Long appointmentId);
    

    Optional<Billing> findByAppointmentId(Long appointmentId);
    List<Billing> findByPatientPhno(String phno);
}