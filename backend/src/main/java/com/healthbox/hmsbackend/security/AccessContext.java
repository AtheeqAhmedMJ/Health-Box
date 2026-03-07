package com.healthbox.hmsbackend.security;

public class AccessContext {

    private static final ThreadLocal<Long> clinicId = new ThreadLocal<>();
    private static final ThreadLocal<Long> doctorId = new ThreadLocal<>();
    private static final ThreadLocal<String> role = new ThreadLocal<>();

    public static void setClinicId(Long id) {
        clinicId.set(id);
    }

    public static Long getClinicId() {
        return clinicId.get();
    }

    public static void setDoctorId(Long id) {
        doctorId.set(id);
    }

    public static Long getDoctorId() {
        return doctorId.get();
    }

    public static void setRole(String r) {
        role.set(r);
    }

    public static String getRole() {
        return role.get();
    }

    public static void clear() {
        clinicId.remove();
        doctorId.remove();
        role.remove();
    }
}