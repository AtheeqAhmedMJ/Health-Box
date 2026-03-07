package com.healthbox.hmsbackend.doctor.service;

import com.healthbox.hmsbackend.appointment.entity.Appointment;
import com.healthbox.hmsbackend.appointment.entity.AppointmentStatus;
import com.healthbox.hmsbackend.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DoctorQueueService {

    private final AppointmentRepository appointmentRepo;

    // ------------------------------------------------
    // CALL NEXT PATIENT
    // ------------------------------------------------
    public Appointment callNext(Long doctorId) {

        LocalDate today = LocalDate.now();

        // finish current consulting automatically
        Appointment current =
                appointmentRepo.findFirstByDoctorIdAndDateAndStatus(
                        doctorId,
                        today,
                        AppointmentStatus.CONSULTING
                );

        if (current != null) {
            current.setStatus(AppointmentStatus.COMPLETED);
            appointmentRepo.save(current);
        }

        Appointment next =
                appointmentRepo.findNextPatient(
                        doctorId,
                        today
                );

        if (next == null)
            throw new RuntimeException("No patients waiting");

        next.setStatus(AppointmentStatus.CONSULTING);

        return appointmentRepo.save(next);
    }

    // ------------------------------------------------
    // START CONSULTATION
    // ------------------------------------------------
    public Appointment startConsultation(Long appointmentId) {

        Appointment a =
                appointmentRepo.findById(appointmentId)
                        .orElseThrow();

        a.setStatus(AppointmentStatus.CONSULTING);

        return appointmentRepo.save(a);
    }

    // ------------------------------------------------
    // COMPLETE CONSULTATION
    // ------------------------------------------------
    public Appointment completeConsultation(Long appointmentId) {

        Appointment a =
                appointmentRepo.findById(appointmentId)
                        .orElseThrow();

        a.setStatus(AppointmentStatus.COMPLETED);

        return appointmentRepo.save(a);
    }

    // ------------------------------------------------
    // SKIP PATIENT
    // ------------------------------------------------
    public Appointment skip(Long appointmentId) {

        Appointment a =
                appointmentRepo.findById(appointmentId)
                        .orElseThrow();

        a.setStatus(AppointmentStatus.NO_SHOW);

        return appointmentRepo.save(a);
    }
}