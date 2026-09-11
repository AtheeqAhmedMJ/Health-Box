package com.healthbox.hms_backend.config;

import com.healthbox.hms_backend.modules.auth.Role;
import com.healthbox.hms_backend.modules.auth.UserRepository;
import com.healthbox.hms_backend.modules.auth.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

// Creates the single platform-owner SUPER_ADMIN account from env vars on first boot.
// There is no self-registration path for this role — it's provisioned out-of-band.
@Component
public class SuperAdminBootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;

    @Value("${superadmin.username:}")
    private String username;
    @Value("${superadmin.password:}")
    private String password;
    @Value("${superadmin.email:}")
    private String email;
    @Value("${superadmin.phno:}")
    private String phno;

    public SuperAdminBootstrap(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return; // not configured — skip silently, no super admin created
        }
        if (userRepository.existsByRole(Role.SUPER_ADMIN)) {
            return; // already provisioned
        }
        userService.saveUser(phno, username, password, email, Role.SUPER_ADMIN, null);
    }
}
