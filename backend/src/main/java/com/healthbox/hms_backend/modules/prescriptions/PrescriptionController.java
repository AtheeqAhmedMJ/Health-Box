package com.healthbox.hms_backend.modules.prescriptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService service;

    public PrescriptionController(PrescriptionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Prescription p) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.create(p));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public List<Prescription> getAll() {
        return service.getAll();
    }

    @GetMapping("/patient/{phno}")
    public List<Prescription> getByPatientPhno(@PathVariable String phno) {
        return service.getByPatientPhno(phno);
    }

    @GetMapping("/appointment/{id}")
    public List<Prescription> getByAppointmentId(@PathVariable Long id) {
        return service.getByAppointmentId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
