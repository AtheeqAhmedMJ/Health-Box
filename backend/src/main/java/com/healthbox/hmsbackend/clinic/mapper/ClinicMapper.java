package com.healthbox.hmsbackend.clinic.mapper;

import com.healthbox.hmsbackend.clinic.dto.*;
import com.healthbox.hmsbackend.clinic.entity.Clinic;
import org.springframework.stereotype.Component;

@Component
public class ClinicMapper {

    public Clinic toEntity(ClinicRequest request){

        Clinic c = new Clinic();
        c.setName(request.getName());
        c.setAddress(request.getAddress());
        c.setPhone(request.getPhone());

        return c;
    }

    public ClinicResponse toResponse(Clinic clinic){

        return new ClinicResponse(
                clinic.getId(),
                clinic.getName(),
                clinic.getAddress(),
                clinic.getPhone()
        );
    }
}