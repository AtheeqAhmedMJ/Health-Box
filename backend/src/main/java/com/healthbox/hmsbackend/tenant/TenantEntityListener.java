package com.healthbox.hmsbackend.tenant;

import com.healthbox.hmsbackend.common.entity.BaseTenantEntity;
import com.healthbox.hmsbackend.security.TenantContext;

import jakarta.persistence.PrePersist;

public class TenantEntityListener {

    @PrePersist
    public void setTenant(Object entity) {

        if (entity instanceof BaseTenantEntity tenantEntity) {

            Long orgId = TenantContext.getOrganizationId();

            if (orgId != null) {
                tenantEntity.setOrganizationId(orgId);
            }
        }
    }
}