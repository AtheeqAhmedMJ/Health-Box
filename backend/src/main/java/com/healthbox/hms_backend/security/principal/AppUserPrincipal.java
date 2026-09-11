package com.healthbox.hms_backend.security.principal;

import com.healthbox.hms_backend.modules.auth.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Security principal carrying the ABAC attributes we authorize on:
 * identity (phno/username), role, and tenant (hospitalId).
 */
@Getter
@AllArgsConstructor
public class AppUserPrincipal implements UserDetails {
    private final String phno;
    private final String username;
    private final Role role;
    private final Long hospitalId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override public String getPassword() { return ""; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
