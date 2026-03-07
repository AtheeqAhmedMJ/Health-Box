package com.healthbox.hmsbackend.tenant;

import com.healthbox.hmsbackend.security.TenantContext;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public class HibernateTenantFilter {

    @PersistenceContext
    private EntityManager entityManager;

    public void enableFilters() {

        Session session = entityManager.unwrap(Session.class);

        // ===============================
        // ORGANIZATION ISOLATION
        // ===============================
        if (TenantContext.getOrganizationId() != null) {

            session.enableFilter("tenantFilter")
                    .setParameter(
                            "organizationId",
                            TenantContext.getOrganizationId());
        }

        // ===============================
        // CLINIC ISOLATION
        // ===============================
        if (TenantContext.getClinicId() != null) {

            session.enableFilter("clinicFilter")
                    .setParameter(
                            "clinicId",
                            TenantContext.getClinicId());
        }

        // ===============================
        // DOCTOR ISOLATION
        // ===============================
        if (TenantContext.getDoctorId() != null) {

            session.enableFilter("doctorFilter")
                    .setParameter(
                            "doctorId",
                            TenantContext.getDoctorId());
        }
    }
}