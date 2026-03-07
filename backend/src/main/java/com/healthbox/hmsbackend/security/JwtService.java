package com.healthbox.hmsbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // ===============================
    // SECRET KEY
    // ===============================
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    // ===============================
    // GENERATE TOKEN
    // ===============================
    public String generateToken(
            String username,
            Long organizationId,
            Long clinicId,
            Long doctorId,
            String role
    ) {

        return Jwts.builder()
                .subject(username)
                .claim("organizationId", organizationId)
                .claim("clinicId", clinicId)
                .claim("doctorId", doctorId)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + expiration)
                )
                .signWith(getKey())
                .compact();
    }

    // ===============================
    // EXTRACT ALL CLAIMS
    // ===============================
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ===============================
    // USERNAME
    // ===============================
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ===============================
    // SAFE LONG EXTRACTION
    // ===============================
    private Long getLongClaim(String token, String key) {

        Object value = extractAllClaims(token).get(key);

        if (value == null) return null;

        if (value instanceof Integer i)
            return i.longValue();

        if (value instanceof Long l)
            return l;

        return Long.parseLong(value.toString());
    }

    // ===============================
    // TENANT DATA
    // ===============================
    public Long extractOrganizationId(String token) {
        return getLongClaim(token, "organizationId");
    }

    public Long extractClinicId(String token) {
        return getLongClaim(token, "clinicId");
    }

    public Long extractDoctorId(String token) {
        return getLongClaim(token, "doctorId");
    }

    public String extractRole(String token) {
        return extractAllClaims(token)
                .get("role", String.class);
    }

    // ===============================
    // EXPIRATION
    // ===============================
    private boolean isTokenExpired(String token) {
        Date expiry = extractAllClaims(token).getExpiration();
        return expiry.before(new Date());
    }

    // ===============================
    // VALIDATION (SPRING SECURITY READY)
    // ===============================
    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ) {

        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }
}