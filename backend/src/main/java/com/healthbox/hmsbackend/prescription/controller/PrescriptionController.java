package com.healthbox.hmsbackend.prescription.controller;

import com.healthbox.hmsbackend.common.response.ApiResponse;
import com.healthbox.hmsbackend.prescription.dto.*;
import com.healthbox.hmsbackend.prescription.mapper.PrescriptionMapper;
import com.healthbox.hmsbackend.prescription.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService service;
    private final PrescriptionMapper mapper;

    @PostMapping
    public ApiResponse<PrescriptionResponse> create(
            @RequestBody PrescriptionRequest request){

        var saved = service.create(
                mapper.toEntity(request)
        );

        return ApiResponse.success(
                mapper.toResponse(saved),
                "Prescription created"
        );
    }

    @GetMapping
    public ApiResponse<List<PrescriptionResponse>> all(){

        return ApiResponse.success(
                service.getAll()
                        .stream()
                        .map(mapper::toResponse)
                        .toList()
        );
    }

    @GetMapping("/patient/{phno}")
    public ApiResponse<List<PrescriptionResponse>> byPatient(
            @PathVariable String phno){

        return ApiResponse.success(
                service.byPatient(phno)
                        .stream()
                        .map(mapper::toResponse)
                        .toList()
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id){

        service.delete(id);
        return ApiResponse.success(null,"Deleted");
    }
}