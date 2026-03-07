package com.healthbox.hmsbackend.appointment.controller;

import com.healthbox.hmsbackend.appointment.entity.Appointment;
import com.healthbox.hmsbackend.appointment.service.WalkInService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/walkin")
@RequiredArgsConstructor
public class WalkInController {

    private final WalkInService walkInService;

    @PostMapping
    public Appointment walkIn(
            @RequestParam String patientPhno,
            @RequestParam Long doctorId,
            @RequestParam Long clinicId,
            @RequestParam String date
    ) {

        return walkInService.createWalkIn(
                patientPhno,
                doctorId,
                clinicId,
                LocalDate.parse(date)
        );
    }
}