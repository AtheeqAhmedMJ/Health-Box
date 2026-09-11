package com.healthbox.hms_backend.security.principal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/** Small helper to pull the ABAC-relevant attributes of the caller out of the SecurityContext. */
@Component
public class CurrentUser {

    public AppUserPrincipal get() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof AppUserPrincipal p)) {
            throw new org.springframework.security.access.AccessDeniedException("Not authenticated");
        }
        return p;
    }
}
