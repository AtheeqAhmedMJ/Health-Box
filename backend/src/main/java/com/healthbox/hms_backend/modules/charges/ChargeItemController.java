package com.healthbox.hms_backend.modules.charges;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/charges")
@PreAuthorize("hasRole('ADMIN')")
public class ChargeItemController {
    private final ChargeItemService service;

    public ChargeItemController(ChargeItemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ChargeItem> create(@RequestBody ChargeItem item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(item));
    }

    @GetMapping
    public List<ChargeItem> getAll() {
        return service.getAll();
    }

    @GetMapping("/active")
    public List<ChargeItem> getActive() {
        return service.getActive();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
