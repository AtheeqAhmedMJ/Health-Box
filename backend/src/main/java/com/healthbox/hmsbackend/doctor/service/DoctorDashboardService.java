package com.healthbox.hmsbackend.doctor.service;

import com.healthbox.hmsbackend.appointment.entity.Appointment;
import com.healthbox.hmsbackend.appointment.entity.AppointmentStatus;
import com.healthbox.hmsbackend.appointment.repository.AppointmentRepository;
import com.healthbox.hmsbackend.patient.entity.Patient;
import com.healthbox.hmsbackend.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DoctorDashboardService {

    private final AppointmentRepository appointmentRepo;
    private final PatientRepository patientRepo;

    // ----------------------------------------------------
    // DOCTOR DASHBOARD
    // ----------------------------------------------------
    public Map<String, Object> dashboard(Long doctorId) {

        LocalDate today = LocalDate.now();

        List<Appointment> appointments =
                appointmentRepo.findByDoctorIdAndDate(
                        doctorId,
                        today
                );

        // SORT BY QUEUE NUMBER
        appointments.sort(
                Comparator.comparing(
                        Appointment::getQueueNumber,
                        Comparator.nullsLast(Integer::compareTo)
                )
        );

        Appointment current = null;
        Appointment next = null;

        int completed = 0;
        int waiting = 0;

        for (Appointment a : appointments) {

            if (a.getStatus() == AppointmentStatus.CONSULTING) {
                current = a;
            }

            if (a.getStatus() == AppointmentStatus.CHECKED_IN && next == null) {
                next = a;
            }

            if (a.getStatus() == AppointmentStatus.COMPLETED) {
                completed++;
            }

            if (a.getStatus() == AppointmentStatus.CHECKED_IN) {
                waiting++;
            }
        }

        Map<String, Object> response = new HashMap<>();

        response.put("currentPatient", buildPatient(current));
        response.put("nextPatient", buildPatient(next));
        response.put("waitingCount", waiting);
        response.put("completedToday", completed);
        response.put("remainingToday", waiting);

        return response;
    }

    // ----------------------------------------------------
    // BUILD PATIENT DATA
    // ----------------------------------------------------
    private Map<String, Object> buildPatient(Appointment a) {

        if (a == null) return null;

        Optional<Patient> pOpt =
                patientRepo.findById(a.getPatientPhno());

        if (pOpt.isEmpty()) return null;

        Patient p = pOpt.get();

        Map<String, Object> data = new HashMap<>();

        data.put("appointmentId", a.getId());
        data.put("name", p.getName());
        data.put("phno", p.getPhno());
        data.put("age", p.getAge());
        data.put("gender", p.getGender());
        data.put("queueNumber", a.getQueueNumber());

        return data;
    }
}