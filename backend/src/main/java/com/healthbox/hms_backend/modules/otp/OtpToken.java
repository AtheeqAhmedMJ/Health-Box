package com.healthbox.hms_backend.modules.otp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp_tokens")
@Getter
@Setter
public class OtpToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String otpHash;

    @Column(nullable = false)
    private String purpose; // REGISTER_ADMIN, REGISTER_PATIENT

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private boolean consumed = false;

    private LocalDateTime createdAt = LocalDateTime.now();
}
