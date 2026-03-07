package com.healthbox.hmsbackend.doctor.mapper;

import com.healthbox.hmsbackend.doctor.dto.*;
import com.healthbox.hmsbackend.doctor.entity.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public Doctor toEntity(DoctorRequest r){

        Doctor d = new Doctor();
        d.setName(r.getName());
        d.setSpecialization(r.getSpecialization());
        d.setPhno(r.getPhno());
        d.setEmail(r.getEmail());

        return d;
    }

    public DoctorResponse toResponse(Doctor d){

        return new DoctorResponse(
                d.getId(),
                d.getName(),
                d.getSpecialization()
        );
    }
}