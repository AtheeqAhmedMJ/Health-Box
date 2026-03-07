package com.healthbox.hmsbackend.billing.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="billing_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long billingId;

    private Long treatmentId;

    private String treatmentName;

    private Double price;

    private Integer quantity;
}