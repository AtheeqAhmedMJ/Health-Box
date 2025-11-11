package com.healthbox.hms_backend.modules.patients;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
@Getter
@Setter
public class Patient {

    @Id
    @Column(name = "phno", nullable = false, unique = true)
    private String phno; // Primary Key

    @Column(nullable = false)
    private String name;

    private Integer age;

    private LocalDate dob;

    private String gender;

    private LocalDateTime createdAt = LocalDateTime.now();
}
