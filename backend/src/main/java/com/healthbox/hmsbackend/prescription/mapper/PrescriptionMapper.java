package com.healthbox.hmsbackend.prescription.mapper;

import com.healthbox.hmsbackend.prescription.dto.*;
import com.healthbox.hmsbackend.prescription.entity.Prescription;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionMapper {

    public Prescription toEntity(PrescriptionRequest r){

        Prescription p = new Prescription();

        p.setPatientPhno(r.getPatientPhno());
        p.setAppointmentId(r.getAppointmentId());
        p.setClinicId(r.getClinicId());
        p.setSymptoms(r.getSymptoms());
        p.setBp(r.getBp());
        p.setSpo2(r.getSpo2());
        p.setTemp(r.getTemp());
        p.setMedicines(r.getMedicines());
        p.setRemarks(r.getRemarks());

        return p;
    }

    public PrescriptionResponse toResponse(Prescription p){

        return new PrescriptionResponse(
                p.getId(),
                p.getPatientPhno(),
                p.getDate()
        );
    }
}