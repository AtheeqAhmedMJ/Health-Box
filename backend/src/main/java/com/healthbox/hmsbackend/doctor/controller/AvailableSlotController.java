package com.healthbox.hmsbackend.doctor.controller;

import com.healthbox.hmsbackend.doctor.service.AvailableSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
public class AvailableSlotController {

    private final AvailableSlotService service;

    @GetMapping("/available")
    public List<LocalTime> availableSlots(
            @RequestParam Long doctorId,
            @RequestParam Long clinicId,
            @RequestParam String date
    ) {

        return service.getAvailableSlots(
                doctorId,
                clinicId,
                LocalDate.parse(date)
        );
    }
}