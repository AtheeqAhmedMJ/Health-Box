package com.healthbox.hms_backend.modules.pharmacy;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PharmacyRepository extends JpaRepository<PharmacyRecord, Long> {
    List<PharmacyRecord> findByHospitalId(Long hospitalId);
    List<PharmacyRecord> findByPrescriptionId(Long prescriptionId);
}
