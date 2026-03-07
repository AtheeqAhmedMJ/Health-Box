package com.healthbox.hmsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HealthBoxApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthBoxApplication.class, args);
    }
}