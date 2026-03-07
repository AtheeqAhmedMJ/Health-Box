package com.healthbox.hmsbackend.prescription.entity;

import com.healthbox.hmsbackend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "prescriptions")
@Getter
@Setter
public class Prescription extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientPhno;

    private Long appointmentId;

    private Long clinicId;

    private String symptoms;

    private String bp;
    private String spo2;
    private String temp;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Map<String,Object>> medicines;

    private String remarks;

    @Column(nullable = false)
    private LocalDate date = LocalDate.now();
}