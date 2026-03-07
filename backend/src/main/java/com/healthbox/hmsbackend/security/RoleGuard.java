package com.healthbox.hmsbackend.security;

public class RoleGuard {

    public static void require(String role) {

        String current =
                TenantContext.getRole();

        if (!role.equals(current)) {
            throw new RuntimeException("Forbidden");
        }
    }
}