package com.healthbox.hmsbackend.appointment.service;

import com.healthbox.hmsbackend.appointment.entity.Appointment;
import com.healthbox.hmsbackend.appointment.entity.AppointmentStatus;
import com.healthbox.hmsbackend.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class WalkInService {

    private final AppointmentRepository appointmentRepo;

    public Appointment createWalkIn(
            String patientPhno,
            Long doctorId,
            Long clinicId,
            LocalDate date
    ) {

        Appointment appt = new Appointment();

        appt.setPatientPhno(patientPhno);
        appt.setDoctorId(doctorId);
        appt.setClinicId(clinicId);

        appt.setDate(date);
        appt.setStartTime(LocalTime.now());

        appt.setStatus(AppointmentStatus.CHECKED_IN);

        // SMART QUEUE NUMBER
        Integer lastQueue =
                appointmentRepo.getLastQueueNumber(
                        doctorId,
                        date
                );

        appt.setQueueNumber(lastQueue + 1);

        return appointmentRepo.save(appt);
    }
}