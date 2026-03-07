package com.healthbox.hmsbackend.billing.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "treatments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String name;

    private String description;

    private Double defaultPrice;
}