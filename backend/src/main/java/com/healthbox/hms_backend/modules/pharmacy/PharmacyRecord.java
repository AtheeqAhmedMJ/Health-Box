package com.healthbox.hms_backend.modules.pharmacy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

// Minimal pharmacy operation: attach a dispense record to an existing prescription.
@Entity
@Table(name = "pharmacy_records")
@Getter
@Setter
public class PharmacyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prescription_id", nullable = false)
    private Long prescriptionId;

    @Column(name = "patient_phno", nullable = false)
    private String patientPhno;

    @Column(name = "hospital_id", nullable = false)
    private Long hospitalId;

    @Column(nullable = false)
    private String status = "PENDING"; // PENDING, DISPENSED

    @Column(name = "dispensed_by")
    private String dispensedBy; // pharmacist phno

    private String notes;

    private LocalDateTime createdAt = LocalDateTime.now();
}
