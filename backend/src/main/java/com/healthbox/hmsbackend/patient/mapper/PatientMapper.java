package com.healthbox.hmsbackend.patient.mapper;

import com.healthbox.hmsbackend.patient.dto.*;
import com.healthbox.hmsbackend.patient.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient toEntity(PatientRequest r) {

        Patient p = new Patient();
        p.setPhno(r.getPhno());
        p.setName(r.getName());
        p.setAge(r.getAge());
        p.setGender(r.getGender());
        p.setClinicId(r.getClinicId());

        return p;
    }

    public PatientResponse toResponse(Patient p) {

        return new PatientResponse(
                p.getPhno(),
                p.getName(),
                p.getAge(),
                p.getGender()
        );
    }
}