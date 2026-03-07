package com.healthbox.hmsbackend.billing.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name="doctor_treatments",
        uniqueConstraints =
        @UniqueConstraint(
                columnNames={"doctorId","treatmentId"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorTreatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long doctorId;

    private Long treatmentId;

    private Double price;
}