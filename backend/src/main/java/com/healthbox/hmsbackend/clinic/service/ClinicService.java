package com.healthbox.hmsbackend.clinic.service;

import com.healthbox.hmsbackend.clinic.entity.Clinic;
import com.healthbox.hmsbackend.clinic.repository.ClinicRepository;
import com.healthbox.hmsbackend.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicService {

    private final ClinicRepository repo;

    public Clinic create(Clinic clinic){
        return repo.save(clinic);
    }

    public Clinic get(Long id){
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Clinic not found"));
    }

    public List<Clinic> getAll(){
        return repo.findAll();
    }

    public void delete(Long id){

        if(!repo.existsById(id)){
            throw new ResourceNotFoundException("Clinic not found");
        }

        repo.deleteById(id);
    }
}