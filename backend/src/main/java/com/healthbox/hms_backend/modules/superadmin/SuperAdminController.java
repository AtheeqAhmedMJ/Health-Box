package com.healthbox.hms_backend.modules.superadmin;

import com.healthbox.hms_backend.modules.auth.Role;
import com.healthbox.hms_backend.modules.auth.UserRepository;
import com.healthbox.hms_backend.modules.patients.PatientRepository;
import com.healthbox.hms_backend.modules.payments.PaymentOrder;
import com.healthbox.hms_backend.modules.payments.PaymentOrderRepository;
import com.healthbox.hms_backend.modules.tenant.Hospital;
import com.healthbox.hms_backend.modules.tenant.HospitalRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

// Platform-owner-only view: how many hospitals/doctors/patients are on the platform,
// and how much platform fee revenue has been collected.
@RestController
@RequestMapping("/api/superadmin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class SuperAdminController {

    private final HospitalRepository hospitalRepo;
    private final UserRepository userRepo;
    private final PatientRepository patientRepo;
    private final PaymentOrderRepository paymentOrderRepo;

    public SuperAdminController(HospitalRepository hospitalRepo, UserRepository userRepo,
                                 PatientRepository patientRepo, PaymentOrderRepository paymentOrderRepo) {
        this.hospitalRepo = hospitalRepo;
        this.userRepo = userRepo;
        this.patientRepo = patientRepo;
        this.paymentOrderRepo = paymentOrderRepo;
    }

    @GetMapping("/stats")
    public Map<String, Object> stats() {
        List<Hospital> hospitals = hospitalRepo.findAll();
        long totalDoctors = userRepo.countByRole(Role.ADMIN);
        long totalPatientAccounts = userRepo.countByRole(Role.PATIENT);
        long totalPatientProfiles = patientRepo.findAll().size();

        List<PaymentOrder> paidOrders = paymentOrderRepo.findByStatus("PAID");
        long totalTransactions = paidOrders.size();
        double totalPlatformRevenue = paidOrders.stream().mapToLong(PaymentOrder::getPlatformFeePaise).sum() / 100.0;
        double totalGrossVolume = paidOrders.stream().mapToLong(PaymentOrder::getAmountPaise).sum() / 100.0;

        LocalDate today = LocalDate.now();
        long transactionsToday = paidOrders.stream()
                .filter(o -> o.getPaidAt() != null && o.getPaidAt().toLocalDate().equals(today))
                .count();

        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("totalHospitals", hospitals.size());
        summary.put("totalDoctors", totalDoctors);
        summary.put("totalPatientAccounts", totalPatientAccounts);
        summary.put("totalPatientProfiles", totalPatientProfiles);
        summary.put("totalTransactions", totalTransactions);
        summary.put("transactionsToday", transactionsToday);
        summary.put("totalPlatformRevenue", totalPlatformRevenue);
        summary.put("totalGrossVolume", totalGrossVolume);
        summary.put("hospitals", hospitalBreakdown(hospitals, paidOrders));
        return summary;
    }

    private List<Map<String, Object>> hospitalBreakdown(List<Hospital> hospitals, List<PaymentOrder> paidOrders) {
        Map<Long, List<PaymentOrder>> byHospital = paidOrders.stream()
                .collect(Collectors.groupingBy(PaymentOrder::getHospitalId));

        List<Map<String, Object>> result = new ArrayList<>();
        for (Hospital h : hospitals) {
            List<PaymentOrder> orders = byHospital.getOrDefault(h.getId(), List.of());
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("hospitalId", h.getId());
            row.put("name", h.getName());
            row.put("code", h.getCode());
            row.put("patients", patientRepo.findByHospitalId(h.getId()).size());
            row.put("transactions", orders.size());
            row.put("platformRevenue", orders.stream().mapToLong(PaymentOrder::getPlatformFeePaise).sum() / 100.0);
            result.add(row);
        }
        return result;
    }
}
