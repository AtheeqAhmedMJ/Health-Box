package com.healthbox.hmsbackend.doctor.service;

import com.healthbox.hmsbackend.common.exception.ResourceNotFoundException;
import com.healthbox.hmsbackend.doctor.entity.Doctor;
import com.healthbox.hmsbackend.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository repo;

    public Doctor create(Doctor doctor){
        return repo.save(doctor);
    }

    public Doctor get(Long id){
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found"));
    }

    public List<Doctor> getAll(){
        return repo.findAll();
    }

    public void delete(Long id){
        repo.deleteById(id);
    }
}