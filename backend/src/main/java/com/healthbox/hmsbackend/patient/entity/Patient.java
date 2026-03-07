package com.healthbox.hmsbackend.patient.entity;

import com.healthbox.hmsbackend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "patients")
@Getter
@Setter
public class Patient extends BaseEntity {

    @Id
    @Column(nullable = false, unique = true)
    private String phno;

    @Column(nullable = false)
    private String name;

    private Integer age;

    private String gender;

    private Long clinicId;
}