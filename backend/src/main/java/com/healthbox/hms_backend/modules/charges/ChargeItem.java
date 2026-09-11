package com.healthbox.hms_backend.modules.charges;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Admin's (doctor's) configurable rate card — e.g. "Consultation" ₹300, "Follow-up" ₹150, "Lab test" ₹500.
@Entity
@Table(name = "charge_items")
@Getter
@Setter
public class ChargeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hospital_id", nullable = false)
    private Long hospitalId;

    @Column(nullable = false)
    private String name;

    @Column(name = "amount_paise", nullable = false)
    private Long amountPaise;

    private boolean active = true;
}
