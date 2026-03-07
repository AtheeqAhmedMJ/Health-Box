package com.healthbox.hmsbackend.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@MappedSuperclass
@Getter
@Setter
@FilterDef(
        name = "tenantFilter",
        parameters = @ParamDef(
                name = "organizationId",
                type = Long.class
        )
)
@Filter(
        name = "tenantFilter",
        condition = "organization_id = :organizationId"
)
public abstract class BaseTenantEntity {

    @Column(name = "organization_id")
    private Long organizationId;
}