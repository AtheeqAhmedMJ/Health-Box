package com.healthbox.hms_backend.modules.pharmacy;

import com.healthbox.hms_backend.modules.prescriptions.Prescription;
import com.healthbox.hms_backend.modules.prescriptions.PrescriptionRepository;
import com.healthbox.hms_backend.security.principal.AppUserPrincipal;
import com.healthbox.hms_backend.security.principal.CurrentUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PharmacyService {

    private final PharmacyRepository repo;
    private final PrescriptionRepository prescriptionRepo;
    private final CurrentUser currentUser;

    public PharmacyService(PharmacyRepository repo, PrescriptionRepository prescriptionRepo, CurrentUser currentUser) {
        this.repo = repo;
        this.prescriptionRepo = prescriptionRepo;
        this.currentUser = currentUser;
    }

    public PharmacyRecord attachToPrescription(Long prescriptionId, String notes) {
        AppUserPrincipal me = currentUser.get();
        Prescription rx = prescriptionRepo.findById(prescriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Prescription not found"));
        if (!rx.getHospitalId().equals(me.getHospitalId())) {
            throw new AccessDeniedException("Cross-tenant access denied");
        }
        PharmacyRecord r = new PharmacyRecord();
        r.setPrescriptionId(prescriptionId);
        r.setPatientPhno(rx.getPatientPhno());
        r.setHospitalId(me.getHospitalId());
        r.setNotes(notes);
        return repo.save(r);
    }

    public PharmacyRecord markDispensed(Long id) {
        AppUserPrincipal me = currentUser.get();
        PharmacyRecord r = repo.findById(id).orElseThrow(() -> new RuntimeException("Pharmacy record not found"));
        if (!r.getHospitalId().equals(me.getHospitalId())) throw new AccessDeniedException("Cross-tenant access denied");
        r.setStatus("DISPENSED");
        r.setDispensedBy(me.getPhno());
        return repo.save(r);
    }

    public List<PharmacyRecord> getAll() {
        return repo.findByHospitalId(currentUser.get().getHospitalId());
    }

    public List<PharmacyRecord> getByPrescription(Long prescriptionId) {
        return repo.findByPrescriptionId(prescriptionId);
    }
}
