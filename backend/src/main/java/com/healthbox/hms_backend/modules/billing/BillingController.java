package com.healthbox.hms_backend.modules.billing;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/billing")
public class BillingController {
    private final BillingService service;

    public BillingController(BillingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Billing b) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.create(b));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public List<Billing> getAll() {
        return service.getAll();
    }

    @GetMapping("/patient/{phno}")
    public List<Billing> getByPatientPhno(@PathVariable String phno) {
        return service.getByPatientPhno(phno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
