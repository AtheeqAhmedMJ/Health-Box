package com.healthbox.hms_backend.modules.billing;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BillingRepository extends JpaRepository<Billing, Long> {
    List<Billing> findByPatientPhno(String phno);
    List<Billing> findByAppointmentId(Long appointmentId);
}
