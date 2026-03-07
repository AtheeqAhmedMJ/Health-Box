package com.healthbox.hmsbackend.schedule.controller;

import com.healthbox.hmsbackend.common.response.ApiResponse;
import com.healthbox.hmsbackend.schedule.dto.DoctorScheduleDTO;
import com.healthbox.hmsbackend.schedule.service.ScheduleEngineService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleEngineService service;

    @GetMapping
    public ApiResponse<List<DoctorScheduleDTO>> schedule(
            @RequestParam Long clinicId,
            @RequestParam String date
    ) {

        return ApiResponse.success(
                service.buildSchedule(
                        clinicId,
                        LocalDate.parse(date)
                )
        );
    }
}