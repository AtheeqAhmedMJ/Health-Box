package com.healthbox.hmsbackend.billing.entity;

import com.healthbox.hmsbackend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="billings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Billing extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clinicId;
    private Long doctorId;
    private String patientPhno;

    private Long appointmentId;

    private Double totalAmount;

    private String paymentStatus; // PENDING / PAID
    private String paymentMode;
}