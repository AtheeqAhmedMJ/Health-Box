package com.healthbox.hms_backend.modules.pharmacy;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pharmacy")
@PreAuthorize("hasRole('ADMIN')")
public class PharmacyController {

    private final PharmacyService service;

    public PharmacyController(PharmacyService service) {
        this.service = service;
    }

    @PostMapping("/attach/{prescriptionId}")
    public ResponseEntity<PharmacyRecord> attach(@PathVariable Long prescriptionId, @RequestBody(required = false) Map<String, String> body) {
        String notes = body != null ? body.get("notes") : null;
        return ResponseEntity.status(HttpStatus.CREATED).body(service.attachToPrescription(prescriptionId, notes));
    }

    @PatchMapping("/{id}/dispense")
    public ResponseEntity<PharmacyRecord> dispense(@PathVariable Long id) {
        return ResponseEntity.ok(service.markDispensed(id));
    }

    @GetMapping
    public List<PharmacyRecord> getAll() {
        return service.getAll();
    }

    @GetMapping("/prescription/{prescriptionId}")
    public List<PharmacyRecord> byPrescription(@PathVariable Long prescriptionId) {
        return service.getByPrescription(prescriptionId);
    }
}
