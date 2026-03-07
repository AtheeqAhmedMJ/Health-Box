package com.healthbox.hmsbackend.security;

public class TenantContext {

    private static final ThreadLocal<Long> organization =
            new ThreadLocal<>();

    private static final ThreadLocal<Long> clinic =
            new ThreadLocal<>();

    private static final ThreadLocal<Long> doctor =
            new ThreadLocal<>();

    private static final ThreadLocal<String> role =
            new ThreadLocal<>();

    // ================= ORGANIZATION
    public static void setOrganizationId(Long id) {
        organization.set(id);
    }

    public static Long getOrganizationId() {
        return organization.get();
    }

    // ================= CLINIC
    public static void setClinicId(Long id) {
        clinic.set(id);
    }

    public static Long getClinicId() {
        return clinic.get();
    }

    // ================= DOCTOR
    public static void setDoctorId(Long id) {
        doctor.set(id);
    }

    public static Long getDoctorId() {
        return doctor.get();
    }

    // ================= ROLE
    public static void setRole(String r) {
        role.set(r);
    }

    public static String getRole() {
        return role.get();
    }

    // ================= CLEAR
    public static void clear() {
        organization.remove();
        clinic.remove();
        doctor.remove();
        role.remove();
    }
}