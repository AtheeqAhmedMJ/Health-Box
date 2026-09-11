package com.healthbox.hms_backend.modules.billing;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingService service;

    public BillingController(BillingService service) {
        this.service = service;
    }

    @GetMapping
    public List<Billing> getAll() {
        return service.getAll();
    }

    @GetMapping("/patient/{phno}")
    public List<Billing> byPatient(@PathVariable String phno) {
        return service.getByPatientPhno(phno);
    }
}
