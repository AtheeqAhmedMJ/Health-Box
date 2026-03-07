package com.healthbox.hmsbackend.patient.controller;

import com.healthbox.hmsbackend.common.response.ApiResponse;
import com.healthbox.hmsbackend.patient.dto.*;
import com.healthbox.hmsbackend.patient.mapper.PatientMapper;
import com.healthbox.hmsbackend.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;
    private final PatientMapper mapper;

    @PostMapping
    public ApiResponse<PatientResponse> create(
            @Valid @RequestBody PatientRequest request) {

        var saved = service.create(
                mapper.toEntity(request)
        );

        return ApiResponse.success(
                mapper.toResponse(saved),
                "Patient created"
        );
    }

    @GetMapping("/{phno}")
    public ApiResponse<PatientResponse> get(@PathVariable String phno) {

        return ApiResponse.success(
                mapper.toResponse(service.get(phno))
        );
    }

    @GetMapping
    public ApiResponse<List<PatientResponse>> all() {

        return ApiResponse.success(
                service.getAll()
                        .stream()
                        .map(mapper::toResponse)
                        .toList()
        );
    }

    @DeleteMapping("/{phno}")
    public ApiResponse<Void> delete(@PathVariable String phno) {

        service.delete(phno);
        return ApiResponse.success(null, "Deleted");
    }
}