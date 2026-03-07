package com.healthbox.hmsbackend.doctor.controller;

import com.healthbox.hmsbackend.common.response.ApiResponse;
import com.healthbox.hmsbackend.doctor.dto.*;
import com.healthbox.hmsbackend.doctor.mapper.DoctorMapper;
import com.healthbox.hmsbackend.doctor.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService service;
    private final DoctorMapper mapper;

    @PostMapping
    public ApiResponse<DoctorResponse> create(
            @Valid @RequestBody DoctorRequest request){

        var doctor = service.create(
                mapper.toEntity(request)
        );

        return ApiResponse.success(
                mapper.toResponse(doctor),
                "Doctor created"
        );
    }

    @GetMapping
    public ApiResponse<List<DoctorResponse>> all(){

        return ApiResponse.success(
                service.getAll()
                        .stream()
                        .map(mapper::toResponse)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<DoctorResponse> get(@PathVariable Long id){

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