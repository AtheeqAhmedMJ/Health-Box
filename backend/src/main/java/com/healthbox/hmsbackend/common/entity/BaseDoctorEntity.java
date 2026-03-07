package com.healthbox.hmsbackend.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Getter
@Setter
@MappedSuperclass

@FilterDef(
        name="doctorFilter",
        parameters=@ParamDef(name="doctorId", type=Long.class)
)

@Filter(
        name="doctorFilter",
        condition="doctor_id = :doctorId"
)
public abstract class BaseDoctorEntity extends BaseClinicEntity {

    @Column(name="doctor_id")
    private Long doctorId;
}