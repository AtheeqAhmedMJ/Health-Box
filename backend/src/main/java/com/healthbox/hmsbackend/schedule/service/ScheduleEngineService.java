package com.healthbox.hmsbackend.schedule.service;

import com.healthbox.hmsbackend.appointment.entity.Appointment;
import com.healthbox.hmsbackend.appointment.repository.AppointmentRepository;
import com.healthbox.hmsbackend.doctor.entity.DoctorAvailability;
import com.healthbox.hmsbackend.doctor.repository.DoctorAvailabilityRepository;
import com.healthbox.hmsbackend.doctor.repository.DoctorRepository;
import com.healthbox.hmsbackend.schedule.dto.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleEngineService {

    private final DoctorAvailabilityRepository availabilityRepo;
    private final AppointmentRepository appointmentRepo;
    private final DoctorRepository doctorRepo;

    private static final int SLOT_DURATION = 15;

    public List<DoctorScheduleDTO> buildSchedule(
            Long clinicId,
            LocalDate date
    ) {

        DayOfWeek day = date.getDayOfWeek();

        List<DoctorAvailability> availability =
                availabilityRepo.findByClinicId(clinicId)
                        .stream()
                        .filter(a -> a.getDayOfWeek() == day)
                        .toList();

        List<DoctorScheduleDTO> schedules = new ArrayList<>();

        for (DoctorAvailability a : availability) {

            var doctor = doctorRepo.findById(a.getDoctorId())
                    .orElseThrow();

            List<Appointment> appointments =
                    appointmentRepo.findByDoctorIdAndDate(
                            a.getDoctorId(),
                            date
                    );

            Set<LocalTime> bookedTimes =
                    appointments.stream()
                            .map(Appointment::getStartTime)
                            .collect(Collectors.toSet());

            List<TimeSlotDTO> slots = new ArrayList<>();

            LocalTime current = a.getStartTime();

            while (current.isBefore(a.getEndTime())) {

                String status =
                        bookedTimes.contains(current)
                                ? "BOOKED"
                                : "FREE";

                slots.add(
                        new TimeSlotDTO(
                                current.toString(),
                                status
                        )
                );

                current = current.plusMinutes(SLOT_DURATION);
            }

            schedules.add(
                    new DoctorScheduleDTO(
                            doctor.getId(),
                            doctor.getName(),
                            slots
                    )
            );
        }

        return schedules;
    }
}