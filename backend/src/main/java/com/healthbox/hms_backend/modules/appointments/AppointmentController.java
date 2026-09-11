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

    // ✅ Create appointment with error handling
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Appointment a) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.create(a));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ Get all appointments
    @GetMapping
    public List<Appointment> getAll() {
        return service.getAll();
    }

    // ✅ Get appointments by patient phone number
    @GetMapping("/patient/{phno}")
    public List<Appointment> getByPatientPhno(@PathVariable String phno) {
        return service.getByPatientPhno(phno);
    }

    // ✅ Get all with patient details
    @GetMapping("/with-details")
    public List<Map<String, Object>> getAllWithPatientDetails() {
        return service.getAllWithPatientDetails();
    }

    // ✅ Delete appointment by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
