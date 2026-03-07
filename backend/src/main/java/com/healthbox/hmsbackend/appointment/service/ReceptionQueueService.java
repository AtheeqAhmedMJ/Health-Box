package com.healthbox.hmsbackend.appointment.service;

import com.healthbox.hmsbackend.appointment.entity.*;
import com.healthbox.hmsbackend.appointment.repository.AppointmentRepository;
import com.healthbox.hmsbackend.billing.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReceptionQueueService {

    private final AppointmentRepository repo;
    private final BillingService billingService;

    // ---------------------------------------
    // COMPLETE CONSULTATION
    // ---------------------------------------
    @Transactional
    public Appointment complete(Long appointmentId){

        Appointment a = repo.findById(appointmentId)
                .orElseThrow();

        a.setStatus(AppointmentStatus.COMPLETED);

        repo.save(a);

        // AUTO BILL TRIGGER
        billingService.autoGenerateConsultationBill(
                a.getClinicId(),
                a.getDoctorId(),
                a.getPatientPhno(),
                a.getId()
        );

        return a;
    }
}