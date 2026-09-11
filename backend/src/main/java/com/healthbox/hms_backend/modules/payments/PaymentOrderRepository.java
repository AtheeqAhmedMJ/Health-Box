package com.healthbox.hms_backend.modules.payments;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
    Optional<PaymentOrder> findByRazorpayOrderId(String razorpayOrderId);
    List<PaymentOrder> findByHospitalId(Long hospitalId);
    List<PaymentOrder> findByPatientPhno(String patientPhno);
    long countByStatus(String status);
    List<PaymentOrder> findByStatus(String status);
}
