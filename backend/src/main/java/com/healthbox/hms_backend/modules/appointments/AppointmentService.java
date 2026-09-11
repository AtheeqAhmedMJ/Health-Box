package com.healthbox.hms_backend.modules.appointments;

import com.healthbox.hms_backend.modules.auth.Role;
import com.healthbox.hms_backend.modules.patients.Patient;
import com.healthbox.hms_backend.modules.patients.PatientRepository;
import com.healthbox.hms_backend.security.principal.AppUserPrincipal;
import com.healthbox.hms_backend.security.principal.CurrentUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

// Booking a slot is free and doesn't require an existing Patient profile —
// the profile only gets created once the consultation itself is paid for.
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final PatientRepository patientRepo;
    private final CurrentUser currentUser;

    public AppointmentService(AppointmentRepository appointmentRepo, PatientRepository patientRepo, CurrentUser currentUser) {
        this.appointmentRepo = appointmentRepo;
        this.patientRepo = patientRepo;
        this.currentUser = currentUser;
    }

    public Appointment create(Appointment a) {
        AppUserPrincipal me = currentUser.get();

        if (me.getRole() == Role.SUPER_ADMIN) throw new AccessDeniedException("Super admin does not manage appointments");
        if (a.getPatientPhno() == null) throw new IllegalArgumentException("Patient phone number is required");
        if (appointmentRepo.existsByPatientPhnoAndDate(a.getPatientPhno(), a.getDate())) {
            throw new IllegalArgumentException("Appointment already exists for this patient on " + a.getDate());
        }
        if (a.getDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Appointment date cannot be in the past.");
        }
        if (me.getRole() == Role.PATIENT && !me.getPhno().equals(a.getPatientPhno())) {
            throw new AccessDeniedException("You can only book for yourself");
        }

        a.setHospitalId(me.getHospitalId());
        a.setDoctorPhno(me.getRole() == Role.ADMIN ? me.getPhno() : a.getDoctorPhno());
        return appointmentRepo.save(a);
    }

    public List<Appointment> getAll() {
        AppUserPrincipal me = currentUser.get();
        return switch (me.getRole()) {
            case ADMIN -> appointmentRepo.findByHospitalId(me.getHospitalId());
            case PATIENT -> appointmentRepo.findByPatientPhno(me.getPhno());
            case SUPER_ADMIN -> throw new AccessDeniedException("Use /api/superadmin endpoints instead");
        };
    }

    public List<Appointment> getByPatientPhno(String phno) {
        AppUserPrincipal me = currentUser.get();
        if (me.getRole() == Role.SUPER_ADMIN) throw new AccessDeniedException("Use /api/superadmin endpoints instead");
        if (me.getRole() == Role.PATIENT && !me.getPhno().equals(phno)) throw new AccessDeniedException("Not your own record");
        List<Appointment> results = appointmentRepo.findByPatientPhno(phno);
        if (me.getRole() == Role.ADMIN) {
            results = results.stream().filter(a -> a.getHospitalId().equals(me.getHospitalId())).toList();
        }
        return results;
    }

    public void delete(Long id) {
        Appointment a = appointmentRepo.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));
        assertAccessible(a);
        appointmentRepo.deleteById(id);
    }

    // Best-effort display join: shows real Patient details if a profile already exists
    // (i.e. they've had at least one paid consultation), otherwise falls back to the
    // name captured at booking time.
    public List<Map<String, Object>> getAllWithPatientDetails() {
        List<Map<String, Object>> response = new ArrayList<>();
        for (Appointment a : getAll()) {
            Optional<Patient> patientOpt = patientRepo.findById(a.getPatientPhno());
            Map<String, Object> entry = new HashMap<>();
            entry.put("appointmentId", a.getId());
            entry.put("date", a.getDate());
            entry.put("status", a.getStatus());
            entry.put("createdAt", a.getCreatedAt());
            entry.put("phno", a.getPatientPhno());
            entry.put("name", patientOpt.map(Patient::getName).orElse(a.getPatientName()));
            entry.put("age", patientOpt.map(Patient::getAge).orElse(null));
            entry.put("gender", patientOpt.map(Patient::getGender).orElse(null));
            entry.put("hasProfile", patientOpt.isPresent());
            response.add(entry);
        }
        return response;
    }

    private void assertAccessible(Appointment a) {
        AppUserPrincipal me = currentUser.get();
        if (me.getRole() == Role.SUPER_ADMIN) throw new AccessDeniedException("Use /api/superadmin endpoints instead");
        if (!a.getHospitalId().equals(me.getHospitalId())) throw new AccessDeniedException("Cross-tenant access denied");
        if (me.getRole() == Role.PATIENT && !me.getPhno().equals(a.getPatientPhno())) throw new AccessDeniedException("Not your own record");
    }
}
