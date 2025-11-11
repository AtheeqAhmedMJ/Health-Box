package com.healthbox.hms_backend.modules.patients;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Patient> create(@RequestBody Patient p) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(p));
    }

    @GetMapping
    public List<Patient> getAll() {
        return service.getAll();
    }

    @GetMapping("/{phno}")
    public ResponseEntity<Patient> getByPhno(@PathVariable String phno) {
        return ResponseEntity.ok(service.getByPhno(phno));
    }

    @DeleteMapping("/{phno}")
    public ResponseEntity<Void> delete(@PathVariable String phno) {
        service.deleteByPhno(phno);
        return ResponseEntity.noContent().build();
    }
}
