package com.healthbox.hms_backend.modules.auth;

public enum Role {
    SUPER_ADMIN, // platform owner — global, not tied to any hospital
    ADMIN,       // the doctor who owns a hospital/practice (tenant)
    PATIENT
}
