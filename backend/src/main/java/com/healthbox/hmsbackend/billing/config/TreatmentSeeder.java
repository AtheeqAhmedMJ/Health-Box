package com.healthbox.hmsbackend.billing.config;

import com.healthbox.hmsbackend.billing.entity.Treatment;
import com.healthbox.hmsbackend.billing.repository.TreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TreatmentSeeder implements CommandLineRunner {

    private final TreatmentRepository repo;

    @Override
    public void run(String... args) {

        if(repo.count() == 0){

            repo.save(new Treatment(
                    null,
                    "Consultation",
                    "Doctor consultation fee",
                    300.0
            ));

            repo.save(new Treatment(
                    null,
                    "Injection",
                    "General injection",
                    150.0
            ));

            repo.save(new Treatment(
                    null,
                    "Dressing",
                    "Wound dressing",
                    200.0
            ));
        }
    }
}