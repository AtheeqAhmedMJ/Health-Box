package com.healthbox.hmsbackend.doctor.service;

import com.healthbox.hmsbackend.doctor.dto.DoctorSlotRequest;
import com.healthbox.hmsbackend.doctor.entity.DoctorSlot;
import com.healthbox.hmsbackend.doctor.repository.DoctorSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class DoctorSlotService {

    private final DoctorSlotRepository repository;

    public DoctorSlot createSlot(DoctorSlotRequest req) {

        DoctorSlot slot = new DoctorSlot();

        slot.setClinicId(req.getClinicId());
        slot.setDoctorId(req.getDoctorId());
        slot.setDayOfWeek(req.getDayOfWeek());

        slot.setStartTime(LocalTime.parse(req.getStartTime()));
        slot.setEndTime(LocalTime.parse(req.getEndTime()));

        slot.setSlotDurationMinutes(req.getSlotDurationMinutes());
        slot.setMaxPatients(req.getMaxPatients());

        return repository.save(slot);
    }
}