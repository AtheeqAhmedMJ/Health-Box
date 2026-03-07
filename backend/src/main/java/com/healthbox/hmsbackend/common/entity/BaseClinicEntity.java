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
        name="clinicFilter",
        parameters=@ParamDef(name="clinicId", type=Long.class)
)

@Filter(
        name="clinicFilter",
        condition="clinic_id = :clinicId"
)
public abstract class BaseClinicEntity extends BaseTenantEntity {

    @Column(name="clinic_id")
    private Long clinicId;
}