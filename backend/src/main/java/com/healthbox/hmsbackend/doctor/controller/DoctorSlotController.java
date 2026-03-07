package com.healthbox.hmsbackend.doctor.controller;

import com.healthbox.hmsbackend.doctor.dto.DoctorSlotRequest;
import com.healthbox.hmsbackend.doctor.entity.DoctorSlot;
import com.healthbox.hmsbackend.doctor.service.DoctorSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
public class DoctorSlotController {

    private final DoctorSlotService service;

    @PostMapping
    public DoctorSlot create(@RequestBody DoctorSlotRequest req) {
        return service.createSlot(req);
    }
}