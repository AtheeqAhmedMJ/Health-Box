package com.healthbox.hms_backend.modules.scheduling;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DoctorSlotRepository extends JpaRepository<DoctorSlot, Long> {
    List<DoctorSlot> findByHospitalIdAndDoctorPhno(Long hospitalId, String doctorPhno);
    List<DoctorSlot> findByHospitalId(Long hospitalId);
}
