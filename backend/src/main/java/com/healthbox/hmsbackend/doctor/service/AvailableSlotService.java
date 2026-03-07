package com.healthbox.hmsbackend.doctor.service;

import com.healthbox.hmsbackend.appointment.repository.AppointmentRepository;
import com.healthbox.hmsbackend.doctor.entity.DoctorAvailability;
import com.healthbox.hmsbackend.doctor.entity.DoctorSlot;
import com.healthbox.hmsbackend.doctor.repository.DoctorAvailabilityRepository;
import com.healthbox.hmsbackend.doctor.repository.DoctorSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailableSlotService {

    private final DoctorAvailabilityRepository availabilityRepo;
    private final DoctorSlotRepository slotRepo;
    private final AppointmentRepository appointmentRepo;
    private final SlotGenerationService generator;

    public List<LocalTime> getAvailableSlots(
            Long doctorId,
            Long clinicId,
            LocalDate date
    ) {

        DayOfWeek day = date.getDayOfWeek();

        // 1️⃣ Doctor availability
        List<DoctorAvailability> availability =
                availabilityRepo.findByDoctorIdAndDayOfWeek(
                        doctorId,
                        day
                );

        if (availability.isEmpty()) {
            return List.of();
        }

        DoctorAvailability a = availability.get(0);

        // 2️⃣ Slot rule
        DoctorSlot slotRule =
                slotRepo.findByDoctorIdAndClinicId(
                        doctorId,
                        clinicId
                ).orElseThrow(
                        () -> new RuntimeException("Slot rule not found")
                );

        // 3️⃣ Generate slots
        List<LocalTime> slots =
                generator.generateSlots(
                        a.getStartTime(),
                        a.getEndTime(),
                        slotRule.getSlotDurationMinutes()
                );

        // 4️⃣ Remove booked slots
        List<LocalTime> booked =
                appointmentRepo.findBookedSlots(
                        doctorId,
                        date
                );

        slots.removeAll(booked);

        return slots;
    }
}