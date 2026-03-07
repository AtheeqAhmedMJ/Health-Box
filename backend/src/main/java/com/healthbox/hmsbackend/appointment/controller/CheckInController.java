package com.healthbox.hmsbackend.appointment.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthbox.hmsbackend.appointment.entity.Appointment;
import com.healthbox.hmsbackend.appointment.service.CheckInService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/checkin")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInService service;

    @PostMapping("/{id}")
    public Appointment checkIn(@PathVariable Long id){
        return service.checkIn(id);
    }
}