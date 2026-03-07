package com.healthbox.hmsbackend.appointment.service;

import com.healthbox.hmsbackend.appointment.entity.*;
import com.healthbox.hmsbackend.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository repo;

    // ONLINE BOOKING
    public Appointment bookAppointment(
            Long doctorId,
            Long clinicId,
            String patientName,
            String phone,
            LocalDate date,
            LocalTime time
    ) {

        // SLOT LOCK SYSTEM
        if(repo.existsByDoctorIdAndDateAndStartTime(
                doctorId, date, time)) {

            throw new RuntimeException("Slot already booked");
        }

        Appointment a = new Appointment();

        a.setDoctorId(doctorId);
        a.setClinicId(clinicId);
        a.setPatientName(patientName);
        a.setPatientPhno(phone);
        a.setDate(date);
        a.setStartTime(time);
        a.setStatus(AppointmentStatus.BOOKED);

        return repo.save(a);
    }
}