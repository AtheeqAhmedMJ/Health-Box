package com.healthbox.hms_backend.modules.prescriptions;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService service;

    public PrescriptionController(PrescriptionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Prescription> getAll() {
        return service.getAll();
    }

    @GetMapping("/patient/{phno}")
    public List<Prescription> byPatient(@PathVariable String phno) {
        return service.getByPatientPhno(phno);
    }

    @GetMapping("/appointment/{id}")
    public List<Prescription> byAppointment(@PathVariable Long id) {
        return service.getByAppointmentId(id);
    }
}
