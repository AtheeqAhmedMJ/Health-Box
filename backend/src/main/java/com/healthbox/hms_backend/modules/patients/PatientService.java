package com.healthbox.hms_backend.modules.patients;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }

    public Patient create(Patient p) {
        return repo.save(p);
    }

    public List<Patient> getAll() {
        return repo.findAll();
    }

    public Patient getByPhno(String phno) {
        return repo.findById(phno)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    public void deleteByPhno(String phno) {
        repo.deleteById(phno);
    }
}
