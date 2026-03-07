package com.healthbox.hmsbackend.billing.controller;

import com.healthbox.hmsbackend.billing.dto.*;
import com.healthbox.hmsbackend.billing.mapper.BillingMapper;
import com.healthbox.hmsbackend.billing.service.BillingService;
import com.healthbox.hmsbackend.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService service;
    private final BillingMapper mapper;

    // --------------------------------------------------
    // CREATE BILL
    // --------------------------------------------------
    @PostMapping("/create")
    public ApiResponse<BillingResponse> createBill(
            @RequestBody BillingRequest req){

        var bill = service.createBill(
                req.getClinicId(),
                req.getDoctorId(),
                req.getPatientPhno(),
                req.getAppointmentId(),
                req.getTreatmentIds()
        );

        return ApiResponse.success(
                mapper.toResponse(bill),
                "Bill Generated"
        );
    }

    // --------------------------------------------------
    // SET DOCTOR TREATMENT PRICE
    // --------------------------------------------------
    @PostMapping("/doctor-price")
    public ApiResponse<?> setDoctorPrice(
            @RequestBody DoctorTreatmentRequest req){

        return ApiResponse.success(
                service.setDoctorPrice(
                        req.getDoctorId(),
                        req.getTreatmentId(),
                        req.getPrice()
                ),
                "Doctor price updated"
        );
    }

    // --------------------------------------------------
    // GET ALL TREATMENTS (FOR UI)
    // --------------------------------------------------
    @GetMapping("/treatments")
    public ApiResponse<?> getTreatments(){
        return ApiResponse.success(
                service.getAllTreatments()
        );
    }

    // --------------------------------------------------
    // MARK BILL PAID
    // --------------------------------------------------
    @PatchMapping("/{id}/pay")
    public ApiResponse<?> payBill(
            @PathVariable Long id,
            @RequestParam String mode){

        return ApiResponse.success(
                service.markPaid(id, mode),
                "Payment completed"
        );
    }
}