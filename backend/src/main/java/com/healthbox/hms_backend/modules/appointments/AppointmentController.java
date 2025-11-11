package com.healthbox.hms_backend.modules.appointments;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Appointment> create(@RequestBody Appointment a) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(a));
    }

    @GetMapping
    public List<Appointment> getAll() {
        return service.getAll();
    }

    @GetMapping("/patient/{phno}")
    public List<Appointment> getByPatientPhno(@PathVariable String phno) {
        return service.getByPatientPhno(phno);
    }

    @GetMapping("/with-details")
    public List<Map<String, Object>> getAllWithPatientDetails() {
        return service.getAllWithPatientDetails();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
