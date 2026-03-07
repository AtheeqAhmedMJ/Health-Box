package com.healthbox.hmsbackend.prescription.service;

import com.healthbox.hmsbackend.common.exception.ResourceNotFoundException;
import com.healthbox.hmsbackend.prescription.entity.Prescription;
import com.healthbox.hmsbackend.prescription.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository repo;

    public Prescription create(Prescription p){
        return repo.save(p);
    }

    public List<Prescription> getAll(){
        return repo.findAll();
    }

    public List<Prescription> byPatient(String phno){
        return repo.findByPatientPhno(phno);
    }

    public void delete(Long id){

        if(!repo.existsById(id)){
            throw new ResourceNotFoundException("Prescription not found");
        }

        repo.deleteById(id);
    }
}