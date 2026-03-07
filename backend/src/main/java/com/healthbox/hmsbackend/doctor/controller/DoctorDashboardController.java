package com.healthbox.hmsbackend.doctor.controller;

import com.healthbox.hmsbackend.common.response.ApiResponse;
import com.healthbox.hmsbackend.doctor.service.DoctorDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor-dashboard")
@RequiredArgsConstructor
public class DoctorDashboardController {

    private final DoctorDashboardService service;

    @GetMapping
    public ApiResponse<?> dashboard(
            @RequestParam Long doctorId) {

        return ApiResponse.success(
                service.dashboard(doctorId)
        );
    }
}