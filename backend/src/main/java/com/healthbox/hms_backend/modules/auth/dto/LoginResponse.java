package com.healthbox.hms_backend.modules.auth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String username;
    private String role;
}
