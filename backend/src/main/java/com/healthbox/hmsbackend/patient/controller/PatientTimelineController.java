package com.healthbox.hmsbackend.patient.controller;

import com.healthbox.hmsbackend.common.response.ApiResponse;
import com.healthbox.hmsbackend.patient.service.PatientTimelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientTimelineController {

    private final PatientTimelineService service;

    @GetMapping("/{phno}/timeline")
    public ApiResponse<?> timeline(
            @PathVariable String phno){

        return ApiResponse.success(
                service.getTimeline(phno)
        );
    }
}