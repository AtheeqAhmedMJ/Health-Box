package com.healthbox.hms_backend.modules.auth;

import com.healthbox.hms_backend.modules.auth.dto.*;
import com.healthbox.hms_backend.modules.otp.OtpService;
import com.healthbox.hms_backend.modules.tenant.Hospital;
import com.healthbox.hms_backend.modules.tenant.HospitalRepository;
import com.healthbox.hms_backend.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final HospitalRepository hospitalRepository;
    private final OtpService otpService;

    // Single login for everyone — the returned role tells the frontend which dashboard to render.
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        User user = userService.findByUsername(request.getUsername());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        String token = jwtTokenProvider.generateToken(user);
        return new LoginResponse(token, user.getUsername(), user.getRole().name(), user.getHospitalId(), user.getPhno());
    }

    // Step 1 of any self-registration: email OTP. purpose = REGISTER_ADMIN | REGISTER_PATIENT
    @PostMapping("/otp/request")
    public void requestOtp(@RequestBody OtpRequest req) {
        otpService.requestOtp(req.getEmail(), req.getPurpose());
    }

    // Doctor self-registers: creates their practice (tenant) + their own ADMIN account.
    @PostMapping("/register-hospital")
    public LoginResponse registerHospital(@RequestBody RegisterHospitalRequest req) {
        otpService.verifyOtp(req.getEmail(), "REGISTER_ADMIN", req.getOtp());

        if (hospitalRepository.existsByCode(req.getHospitalCode())) {
            throw new IllegalArgumentException("Hospital code already in use");
        }
        Hospital hospital = new Hospital();
        hospital.setName(req.getHospitalName());
        hospital.setCode(req.getHospitalCode());
        hospital = hospitalRepository.save(hospital);

        User admin = userService.saveUser(req.getAdminPhno(), req.getUsername(), req.getPassword(),
                req.getEmail(), Role.ADMIN, hospital.getId());
        String token = jwtTokenProvider.generateToken(admin);
        return new LoginResponse(token, admin.getUsername(), admin.getRole().name(), admin.getHospitalId(), admin.getPhno());
    }

    // Patient self-registers for free under a doctor's practice (identified by hospitalCode).
    // This only creates a login identity — the clinical Patient profile is created later,
    // gated behind the first paid consultation.
    @PostMapping("/register-patient")
    public LoginResponse registerPatient(@RequestBody RegisterPatientRequest req) {
        otpService.verifyOtp(req.getEmail(), "REGISTER_PATIENT", req.getOtp());

        Hospital hospital = hospitalRepository.findByCode(req.getHospitalCode())
                .orElseThrow(() -> new IllegalArgumentException("Unknown hospital code"));

        User patient = userService.saveUser(req.getPhno(), req.getUsername(), req.getPassword(),
                req.getEmail(), Role.PATIENT, hospital.getId());
        String token = jwtTokenProvider.generateToken(patient);
        return new LoginResponse(token, patient.getUsername(), patient.getRole().name(), patient.getHospitalId(), patient.getPhno());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }
}
