package com.healthbox.hmsbackend.organization.repository;

import com.healthbox.hmsbackend.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository
        extends JpaRepository<Organization, Long> {
}