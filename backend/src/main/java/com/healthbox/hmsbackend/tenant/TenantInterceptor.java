package com.healthbox.hmsbackend.tenant;

import com.healthbox.hmsbackend.security.TenantContext;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TenantInterceptor extends OncePerRequestFilter {

    private final HibernateTenantFilter tenantFilter;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {

            // ✅ Enable Hibernate Tenant Filters
            tenantFilter.enableFilters();

            filterChain.doFilter(request, response);

        } finally {
            // ✅ VERY IMPORTANT
            // prevents tenant leaking between threads
            TenantContext.clear();
        }
    }
}