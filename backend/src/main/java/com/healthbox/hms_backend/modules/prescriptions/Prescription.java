package com.healthbox.hms_backend.modules.prescriptions;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientPhno;
    private Long appointmentId;

    private String symptoms;
    private String bp;
    private String spo2;
    private String grbs;
    private String temp;

    // ✅ List of prescribed medicines (JSONB)
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Map<String, Object>> medicines;

    private String remarks;

    // ✅ Billing info (JSONB)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> billing;

    // ✅ Add this for date-based queries (daily/monthly reports)
    @Column(nullable = false)
    private LocalDate date = LocalDate.now();

    private LocalDateTime createdAt = LocalDateTime.now();

    // ------------------- Getters & Setters -------------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPatientPhno() { return patientPhno; }
    public void setPatientPhno(String patientPhno) { this.patientPhno = patientPhno; }

    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }

    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }

    public String getBp() { return bp; }
    public void setBp(String bp) { this.bp = bp; }

    public String getSpo2() { return spo2; }
    public void setSpo2(String spo2) { this.spo2 = spo2; }

    public String getGrbs() { return grbs; }
    public void setGrbs(String grbs) { this.grbs = grbs; }

    public String getTemp() { return temp; }
    public void setTemp(String temp) { this.temp = temp; }

    public List<Map<String, Object>> getMedicines() { return medicines; }
    public void setMedicines(List<Map<String, Object>> medicines) { this.medicines = medicines; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Map<String, Object> getBilling() { return billing; }
    public void setBilling(Map<String, Object> billing) { this.billing = billing; }

    public LocalDate getDate() { return date; }     // ✅ Required for dashboard summaries
    public void setDate(LocalDate date) { this.date = date; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
