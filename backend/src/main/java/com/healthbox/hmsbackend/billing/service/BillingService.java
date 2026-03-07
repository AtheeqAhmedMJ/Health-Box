package com.healthbox.hmsbackend.billing.service;

import com.healthbox.hmsbackend.billing.entity.*;
import com.healthbox.hmsbackend.billing.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BillingService {

    private final BillingRepository billingRepo;
    private final BillingItemRepository itemRepo;
    private final TreatmentRepository treatmentRepo;
    private final DoctorTreatmentRepository doctorTreatmentRepo;

    // --------------------------------------------------
    // NORMAL BILL CREATION
    // --------------------------------------------------
    public Billing createBill(
            Long clinicId,
            Long doctorId,
            String patientPhno,
            Long appointmentId,
            List<Long> treatmentIds
    ){

        Billing bill = new Billing();

        bill.setClinicId(clinicId);
        bill.setDoctorId(doctorId);
        bill.setPatientPhno(patientPhno);
        bill.setAppointmentId(appointmentId);
        bill.setPaymentStatus("PENDING");

        bill = billingRepo.save(bill);

        double total = 0;

        for(Long treatmentId : treatmentIds){

            Treatment treatment =
                    treatmentRepo.findById(treatmentId)
                            .orElseThrow();

            double price =
                    doctorTreatmentRepo
                            .findByDoctorIdAndTreatmentId(
                                    doctorId,
                                    treatmentId
                            )
                            .map(DoctorTreatment::getPrice)
                            .orElse(treatment.getDefaultPrice());

            BillingItem item = new BillingItem();

            item.setBillingId(bill.getId());
            item.setTreatmentId(treatment.getId());
            item.setTreatmentName(treatment.getName());
            item.setPrice(price);
            item.setQuantity(1);

            itemRepo.save(item);

            total += price;
        }

        bill.setTotalAmount(total);

        return billingRepo.save(bill);
    }

    // --------------------------------------------------
    // AUTO BILL GENERATION (CORE FEATURE)
    // --------------------------------------------------
    public Billing autoGenerateConsultationBill(
            Long clinicId,
            Long doctorId,
            String patientPhno,
            Long appointmentId
    ){

        // prevent duplicate billing
        if(billingRepo.existsByAppointmentId(appointmentId)){
            return billingRepo
                    .findByAppointmentId(appointmentId)
                    .orElseThrow();
        }

        Treatment consultation =
                treatmentRepo.findAll()
                        .stream()
                        .filter(t ->
                                t.getName()
                                        .equalsIgnoreCase("Consultation"))
                        .findFirst()
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Consultation treatment missing"));

        return createBill(
                clinicId,
                doctorId,
                patientPhno,
                appointmentId,
                List.of(consultation.getId())
        );
    }

    // --------------------------------------------------
    // DOCTOR CUSTOM PRICE
    // --------------------------------------------------
    public DoctorTreatment setDoctorPrice(
            Long doctorId,
            Long treatmentId,
            Double price
    ){

        DoctorTreatment dt =
                doctorTreatmentRepo
                        .findByDoctorIdAndTreatmentId(
                                doctorId,
                                treatmentId
                        )
                        .orElse(new DoctorTreatment());

        dt.setDoctorId(doctorId);
        dt.setTreatmentId(treatmentId);
        dt.setPrice(price);

        return doctorTreatmentRepo.save(dt);
    }

    // --------------------------------------------------
    // PAYMENT COMPLETE
    // --------------------------------------------------
    public Billing markPaid(Long billingId, String mode){

        Billing bill = billingRepo.findById(billingId)
                .orElseThrow();

        bill.setPaymentStatus("PAID");
        bill.setPaymentMode(mode);

        return billingRepo.save(bill);
    }

    // --------------------------------------------------
    // GET TREATMENTS
    // --------------------------------------------------
    public List<Treatment> getAllTreatments(){
        return treatmentRepo.findAll();
    }
}