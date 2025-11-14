package com.healthbox.hms_backend.modules.billing;

import com.healthbox.hms_backend.modules.appointments.AppointmentRepository;
import com.healthbox.hms_backend.modules.prescriptions.PrescriptionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BillingService {
    private final BillingRepository billingRepo;
    private final AppointmentRepository appointmentRepo;
    private final PrescriptionRepository prescriptionRepo;

    public BillingService(BillingRepository billingRepo,
                          AppointmentRepository appointmentRepo,
                          PrescriptionRepository prescriptionRepo) {
        this.billingRepo = billingRepo;
        this.appointmentRepo = appointmentRepo;
        this.prescriptionRepo = prescriptionRepo;
    }

    public Billing create(Billing b) {
        if (!appointmentRepo.existsById(b.getAppointmentId())) {
            throw new IllegalArgumentException("No appointment found for billing.");
        }
        if (b.getTotalAmount() == null) {
            double total = (b.getConsultationFee() != null ? b.getConsultationFee() : 0)
                         + (b.getMedicineCharges() != null ? b.getMedicineCharges() : 0)
                         + (b.getLabCharges() != null ? b.getLabCharges() : 0)
                         + (b.getOtherCharges() != null ? b.getOtherCharges() : 0);
            b.setTotalAmount(total);
        }
        return billingRepo.save(b);
    }

    public List<Billing> getAll() {
        return billingRepo.findAll();
    }

    public List<Billing> getByPatientPhno(String phno) {
        return billingRepo.findByPatientPhno(phno);
    }

    public void delete(Long id) {
        billingRepo.deleteById(id);
    }
}
