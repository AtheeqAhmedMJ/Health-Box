package com.healthbox.hms_backend.modules.prescriptions;

import com.healthbox.hms_backend.modules.patients.PatientRepository;
import com.healthbox.hms_backend.modules.appointments.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepo;
    private final PatientRepository patientRepo;
    private final AppointmentRepository appointmentRepo;

    public PrescriptionService(PrescriptionRepository prescriptionRepo, PatientRepository patientRepo, AppointmentRepository appointmentRepo) {
        this.prescriptionRepo = prescriptionRepo;
        this.patientRepo = patientRepo;
        this.appointmentRepo = appointmentRepo;
    }

    public Prescription create(Prescription p) {
        // Step 1 — Check patient existence
        if (!patientRepo.existsById(p.getPatientPhno())) {
            throw new IllegalArgumentException("No patient found with phone number: " + p.getPatientPhno());
        }

        // Step 2 — Check valid appointment for this patient
        boolean hasAppointment = appointmentRepo.findByPatientPhno(p.getPatientPhno())
                .stream()
                .anyMatch(a -> a.getId().equals(p.getAppointmentId()));

        if (!hasAppointment) {
            throw new IllegalArgumentException("Prescription cannot be created — no valid appointment found for this patient.");
        }

        // Step 3 — Save prescription
        return prescriptionRepo.save(p);
    }

    public List<Prescription> getAll() {
        return prescriptionRepo.findAll();
    }

    public List<Prescription> getByPatientPhno(String phno) {
        return prescriptionRepo.findByPatientPhno(phno);
    }

    public List<Prescription> getByAppointmentId(Long id) {
        return prescriptionRepo.findByAppointmentId(id);
    }

    public void delete(Long id) {
        prescriptionRepo.deleteById(id);
    }
}
