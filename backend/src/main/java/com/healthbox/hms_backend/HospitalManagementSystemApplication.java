package com.healthbox.hms_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HospitalManagementSystemApplication {

    public static void main(String[] args) {
        System.out.println(
            new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("1234")
        );

        SpringApplication.run(HospitalManagementSystemApplication.class, args);
    }
}

