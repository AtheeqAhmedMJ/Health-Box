package com.healthbox.hms_backend.modules.appointments;

import com.healthbox.hms_backend.modules.patients.Patient;
import com.healthbox.hms_backend.modules.patients.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final PatientRepository patientRepo;

    public AppointmentService(AppointmentRepository appointmentRepo, PatientRepository patientRepo) {
        this.appointmentRepo = appointmentRepo;
        this.patientRepo = patientRepo;
    }

    // Create a new appointment
    public Appointment create(Appointment a) {
        // Ensure the patient exists
        if (!patientRepo.existsById(a.getPatientPhno())) {
            throw new IllegalArgumentException("No patient found with phone number: " + a.getPatientPhno());
        }
        return appointmentRepo.save(a);
    }

    // Get all appointments (raw)
    public List<Appointment> getAll() {
        return appointmentRepo.findAll();
    }

    // Get by patient phone number
    public List<Appointment> getByPatientPhno(String phno) {
        return appointmentRepo.findByPatientPhno(phno);
    }

    // Delete appointment
    public void delete(Long id) {
        appointmentRepo.deleteById(id);
    }

    // Get combined data with patient details
    public List<Map<String, Object>> getAllWithPatientDetails() {
        List<Appointment> appointments = appointmentRepo.findAll();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Appointment a : appointments) {
            Optional<Patient> patientOpt = patientRepo.findById(a.getPatientPhno());
            if (patientOpt.isPresent()) {
                Patient p = patientOpt.get();
                Map<String, Object> entry = new HashMap<>();
                entry.put("appointmentId", a.getId());
                entry.put("date", a.getDate());
                entry.put("createdAt", a.getCreatedAt());
                entry.put("phno", p.getPhno());
                entry.put("name", p.getName());
                entry.put("age", p.getAge());
                entry.put("gender", p.getGender());
                response.add(entry);
            }
        }

        return response;
    }
}
