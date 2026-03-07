package com.healthbox.hmsbackend.doctor.controller;

import com.healthbox.hmsbackend.appointment.entity.Appointment;
import com.healthbox.hmsbackend.doctor.service.DoctorQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor/queue")
@RequiredArgsConstructor
public class DoctorQueueController {

    private final DoctorQueueService queueService;

    // CALL NEXT
    @PostMapping("/call-next")
    public Appointment callNext(
            @RequestParam Long doctorId
    ) {
        return queueService.callNext(doctorId);
    }

    // START
    @PostMapping("/start/{id}")
    public Appointment start(@PathVariable Long id) {
        return queueService.startConsultation(id);
    }

    // COMPLETE
    @PostMapping("/complete/{id}")
    public Appointment complete(@PathVariable Long id) {
        return queueService.completeConsultation(id);
    }

    // SKIP
    @PostMapping("/skip/{id}")
    public Appointment skip(@PathVariable Long id) {
        return queueService.skip(id);
    }
}