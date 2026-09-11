package com.healthbox.hms_backend.modules.payments;

import com.healthbox.hms_backend.modules.billing.Billing;
import com.healthbox.hms_backend.modules.billing.BillingRepository;
import com.healthbox.hms_backend.modules.patients.Patient;
import com.healthbox.hms_backend.modules.patients.PatientRepository;
import com.healthbox.hms_backend.modules.prescriptions.Prescription;
import com.healthbox.hms_backend.modules.prescriptions.PrescriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Separate bean (not a method on PaymentService) so @Transactional is honoured —
 * calling it from within the same class would bypass the Spring proxy and silently
 * skip the transaction, breaking the all-or-nothing guarantee this depends on.
 */
@Service
public class ConsultationMaterializer {

    private final PaymentOrderRepository orderRepo;
    private final PatientRepository patientRepo;
    private final PrescriptionRepository prescriptionRepo;
    private final BillingRepository billingRepo;

    public ConsultationMaterializer(PaymentOrderRepository orderRepo, PatientRepository patientRepo,
                                     PrescriptionRepository prescriptionRepo, BillingRepository billingRepo) {
        this.orderRepo = orderRepo;
        this.patientRepo = patientRepo;
        this.prescriptionRepo = prescriptionRepo;
        this.billingRepo = billingRepo;
    }

    @Transactional
    public void materialize(PaymentOrder po, String razorpayPaymentId) {
        if ("PAID".equals(po.getStatus())) return; // idempotency guard

        Map<String, Object> payload = po.getPayload();

        Patient patient = patientRepo.findById(po.getPatientPhno()).orElseGet(Patient::new);
        patient.setPhno(po.getPatientPhno());
        patient.setName((String) payload.get("patientName"));
        if (payload.get("patientAge") != null) patient.setAge(((Number) payload.get("patientAge")).intValue());
        if (payload.get("patientGender") != null) patient.setGender((String) payload.get("patientGender"));
        patient.setHospitalId(po.getHospitalId());
        if (patient.getAssignedDoctorPhno() == null) patient.setAssignedDoctorPhno(po.getDoctorPhno());
        patientRepo.save(patient);

        Prescription rx = new Prescription();
        rx.setPatientPhno(po.getPatientPhno());
        rx.setAppointmentId(po.getAppointmentId());
        rx.setSymptoms((String) payload.get("symptoms"));
        rx.setBp((String) payload.get("bp"));
        rx.setSpo2((String) payload.get("spo2"));
        rx.setGrbs((String) payload.get("grbs"));
        rx.setTemp((String) payload.get("temp"));
        Object meds = payload.get("medicines");
        if (meds instanceof List) rx.setMedicines((List<Map<String, Object>>) meds);
        rx.setRemarks((String) payload.get("remarks"));
        rx.setHospitalId(po.getHospitalId());
        rx.setDoctorPhno(po.getDoctorPhno());
        prescriptionRepo.save(rx);

        Billing bill = new Billing();
        bill.setPatientPhno(po.getPatientPhno());
        bill.setAppointmentId(po.getAppointmentId());
        bill.setTotalAmount(po.getAmountPaise() / 100.0);
        bill.setPaymentStatus("PAID");
        bill.setPaymentMode("ONLINE");
        bill.setHospitalId(po.getHospitalId());
        billingRepo.save(bill);

        po.setStatus("PAID");
        po.setPaidAt(LocalDateTime.now());
        po.setRazorpayPaymentId(razorpayPaymentId);
        orderRepo.save(po);
    }
}
