package com.healthbox.hmsbackend.patient.service;

import com.healthbox.hmsbackend.appointment.entity.Appointment;
import com.healthbox.hmsbackend.appointment.repository.AppointmentRepository;
import com.healthbox.hmsbackend.billing.entity.Billing;
import com.healthbox.hmsbackend.billing.repository.BillingRepository;
import com.healthbox.hmsbackend.patient.dto.PatientTimelineDTO;
import com.healthbox.hmsbackend.patient.entity.Patient;
import com.healthbox.hmsbackend.patient.repository.PatientRepository;
import com.healthbox.hmsbackend.prescription.entity.Prescription;
import com.healthbox.hmsbackend.prescription.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PatientTimelineService {

    private final PatientRepository patientRepo;
    private final AppointmentRepository appointmentRepo;
    private final PrescriptionRepository prescriptionRepo;
    private final BillingRepository billingRepo;

    // --------------------------------------------------
    // PATIENT TIMELINE
    // --------------------------------------------------
    public Map<String,Object> getTimeline(String phno){

        Patient patient =
                patientRepo.findById(phno)
                        .orElseThrow();

        List<Appointment> appointments =
                appointmentRepo.findByPatientPhno(phno);

        List<Prescription> prescriptions =
                prescriptionRepo.findByPatientPhno(phno);

        List<Billing> bills =
                billingRepo.findByPatientPhno(phno);

        Map<Long, Prescription> prescriptionMap =
                new HashMap<>();

        for(Prescription p : prescriptions){
            prescriptionMap.put(p.getAppointmentId(), p);
        }

        Map<Long, Billing> billingMap =
                new HashMap<>();

        for(Billing b : bills){
            billingMap.put(b.getAppointmentId(), b);
        }

        List<PatientTimelineDTO> timeline = new ArrayList<>();

        for(Appointment a : appointments){

            PatientTimelineDTO dto =
                    new PatientTimelineDTO();

            dto.setAppointmentId(a.getId());
            dto.setDate(a.getDate());
            dto.setDoctorId(a.getDoctorId());
            dto.setStatus(a.getStatus().name());

            dto.setPrescription(
                    prescriptionMap.get(a.getId())
            );

            dto.setBilling(
                    billingMap.get(a.getId())
            );

            timeline.add(dto);
        }

        timeline.sort(
                Comparator.comparing(
                        PatientTimelineDTO::getDate
                ).reversed()
        );

        Map<String,Object> response =
                new HashMap<>();

        response.put("patient", patient);
        response.put("timeline", timeline);

        return response;
    }
}