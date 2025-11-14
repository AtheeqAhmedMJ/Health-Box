package com.healthbox.hms_backend.modules.dashboard;

import com.healthbox.hms_backend.modules.patients.PatientRepository;
import com.healthbox.hms_backend.modules.appointments.AppointmentRepository;
import com.healthbox.hms_backend.modules.prescriptions.PrescriptionRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard/summary")
public class DashboardController {

    private final AppointmentRepository appointmentRepo;
    private final PatientRepository patientRepo;
    private final PrescriptionRepository prescriptionRepo;

    public DashboardController(
            AppointmentRepository appointmentRepo,
            PatientRepository patientRepo,
            PrescriptionRepository prescriptionRepo
    ) {
        this.appointmentRepo = appointmentRepo;
        this.patientRepo = patientRepo;
        this.prescriptionRepo = prescriptionRepo;
    }

    @GetMapping
    public Map<String, Object> getDashboardSummary() {
        Map<String, Object> summary = new LinkedHashMap<>();

        LocalDate today = LocalDate.now();
        YearMonth thisMonth = YearMonth.now();

        // 🧠 1️⃣ Today's Appointments (dynamic)
        long todaysAppointments = appointmentRepo.findAll().stream()
                .filter(a -> today.equals(a.getDate()))
                .count();

        // 🧠 2️⃣ Total Patient Records (dynamic)
        long totalPatients = patientRepo.count();

        // 🧠 3️⃣ Monthly Appointments (dynamic)
        long monthlyAppointments = appointmentRepo.findAll().stream()
                .filter(a -> thisMonth.equals(YearMonth.from(a.getDate())))
                .count();

        // 🧠 4️⃣ Recurring Patients (%) — based on appointments table
        Map<String, Long> appointmentsPerPatient = appointmentRepo.findAll().stream()
                .collect(Collectors.groupingBy(a -> a.getPatientPhno(), Collectors.counting()));

        long recurringPatients = appointmentsPerPatient.values().stream()
                .filter(count -> count > 1)
                .count();

        double recurringPercentage = totalPatients == 0 ? 0 : (recurringPatients * 100.0 / totalPatients);

        // 🧠 5️⃣ Total Prescriptions This Month (from prescriptions table)
        long prescriptionsThisMonth = prescriptionRepo.findAll().stream()
                .filter(p -> thisMonth.equals(YearMonth.from(p.getDate())))
                .count();

        // 🧠 6️⃣ Appointments Graph Data (past 7 days)
        LocalDate weekStart = today.minusDays(6);
        Map<String, Long> appointmentsByDay = appointmentRepo.findAll().stream()
                .filter(a -> !a.getDate().isBefore(weekStart))
                .collect(Collectors.groupingBy(
                        a -> a.getDate().toString(),
                        TreeMap::new,
                        Collectors.counting()
                ));

        // Fill missing days with 0 so graph stays continuous
        for (int i = 0; i < 7; i++) {
            LocalDate date = weekStart.plusDays(i);
            appointmentsByDay.putIfAbsent(date.toString(), 0L);
        }

        // ✅ Response sent to frontend — all live from DB
        summary.put("todaysAppointments", todaysAppointments);
        summary.put("totalPatients", totalPatients);
        summary.put("monthlyAppointments", monthlyAppointments);
        summary.put("recurringPatientsPercentage", String.format("%.1f", recurringPercentage));
        summary.put("prescriptionsThisMonth", prescriptionsThisMonth);
        summary.put("graphData", appointmentsByDay);

        return summary;
    }
}
