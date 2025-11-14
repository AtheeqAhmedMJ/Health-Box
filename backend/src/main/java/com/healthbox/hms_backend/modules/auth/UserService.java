package com.healthbox.hms_backend.modules.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

public User saveUser(String phno, String username, String password, Role role) {
    User user = User.builder()
            .phno(phno)
            .username(username)
            .password(passwordEncoder.encode(password))
            .role(role)
            .build();
    return userRepository.save(user);
}


    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
