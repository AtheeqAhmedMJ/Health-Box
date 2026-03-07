package com.healthbox.hmsbackend.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TenantAwareRepository<T, ID>
        extends JpaRepository<T, ID> {
}