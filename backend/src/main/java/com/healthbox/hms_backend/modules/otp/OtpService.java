package com.healthbox.hms_backend.modules.otp;

import com.healthbox.hms_backend.modules.mail.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class OtpService {

    private static final int TTL_MINUTES = 10;
    private final OtpRepository repo;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final SecureRandom random = new SecureRandom();

    public OtpService(OtpRepository repo, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public void requestOtp(String email, String purpose) {
        String otp = String.format("%06d", random.nextInt(1_000_000));

        OtpToken token = new OtpToken();
        token.setEmail(email);
        token.setPurpose(purpose);
        token.setOtpHash(passwordEncoder.encode(otp));
        token.setExpiresAt(LocalDateTime.now().plusMinutes(TTL_MINUTES));
        repo.save(token);

        emailService.send(email, "Your HealthBox verification code",
                "Your OTP is " + otp + ". It expires in " + TTL_MINUTES + " minutes. Do not share this code.");
    }

    public void verifyOtp(String email, String purpose, String otp) {
        OtpToken token = repo.findFirstByEmailAndPurposeAndConsumedFalseOrderByCreatedAtDesc(email, purpose)
                .orElseThrow(() -> new IllegalArgumentException("No OTP requested for this email"));

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("OTP expired, please request a new one");
        }
        if (!passwordEncoder.matches(otp, token.getOtpHash())) {
            throw new IllegalArgumentException("Incorrect OTP");
        }
        token.setConsumed(true);
        repo.save(token);
    }
}
