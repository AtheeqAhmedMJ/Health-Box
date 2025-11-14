package com.healthbox.hms_backend.security.jwt;

import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtUtils(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String getUsername(String token) {
        return jwtTokenProvider.extractUsername(token);
    }

    public boolean isValid(String token) {
        return jwtTokenProvider.validateToken(token);
    }
}
