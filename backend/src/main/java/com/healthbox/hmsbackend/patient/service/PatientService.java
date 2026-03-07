package com.healthbox.hmsbackend.patient.service;

import com.healthbox.hmsbackend.common.exception.ResourceNotFoundException;
import com.healthbox.hmsbackend.patient.entity.Patient;
import com.healthbox.hmsbackend.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository repo;

    public Patient create(Patient patient) {
        return repo.save(patient);
    }

    public Patient get(String phno) {
        return repo.findById(phno)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found"));
    }

    public List<Patient> getAll() {
        return repo.findAll();
    }

    public void delete(String phno) {

        if (!repo.existsById(phno)) {
            throw new ResourceNotFoundException("Patient not found");
        }

        repo.deleteById(phno);
    }
}