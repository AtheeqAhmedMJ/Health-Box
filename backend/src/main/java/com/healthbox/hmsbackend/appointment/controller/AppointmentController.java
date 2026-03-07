package com.healthbox.hmsbackend.appointment.controller;

import com.healthbox.hmsbackend.appointment.entity.Appointment;
import com.healthbox.hmsbackend.appointment.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.*;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping
    public Appointment book(
            @RequestParam Long doctorId,
            @RequestParam Long clinicId,
            @RequestParam String patientName,
            @RequestParam String phone,
            @RequestParam String date,
            @RequestParam String time
    ){
        return service.bookAppointment(
                doctorId,
                clinicId,
                patientName,
                phone,
                LocalDate.parse(date),
                LocalTime.parse(time)
        );
    }
}