package com.healthbox.hmsbackend.appointment.controller;

import com.healthbox.hmsbackend.appointment.entity.Appointment;
import com.healthbox.hmsbackend.appointment.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/queue")
@RequiredArgsConstructor
public class QueueController {

    private final QueueService service;

    @PostMapping("/next/{doctorId}")
    public Appointment next(@PathVariable Long doctorId){
        return service.callNext(doctorId);
    }
}