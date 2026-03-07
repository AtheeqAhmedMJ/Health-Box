package com.healthbox.hmsbackend.clinic.controller;

import com.healthbox.hmsbackend.clinic.dto.*;
import com.healthbox.hmsbackend.clinic.mapper.ClinicMapper;
import com.healthbox.hmsbackend.clinic.service.ClinicService;
import com.healthbox.hmsbackend.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clinics")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService service;
    private final ClinicMapper mapper;

    @PostMapping
    public ApiResponse<ClinicResponse> create(
            @Valid @RequestBody ClinicRequest request){

        var saved = service.create(
                mapper.toEntity(request)
        );

        return ApiResponse.success(
                mapper.toResponse(saved),
                "Clinic created"
        );
    }

    @GetMapping
    public ApiResponse<List<ClinicResponse>> all(){

        return ApiResponse.success(
                service.getAll()
                        .stream()
                        .map(mapper::toResponse)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<ClinicResponse> get(@PathVariable Long id){

        return ApiResponse.success(
                mapper.toResponse(service.get(id))
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id){

        service.delete(id);
        return ApiResponse.success(null,"Deleted");
    }
}