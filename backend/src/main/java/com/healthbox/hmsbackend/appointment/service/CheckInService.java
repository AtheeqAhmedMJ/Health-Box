package com.healthbox.hmsbackend.appointment.service;

import com.healthbox.hmsbackend.appointment.entity.*;
import com.healthbox.hmsbackend.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final AppointmentRepository repo;

    public Appointment checkIn(Long id){

        Appointment a = repo.findById(id).orElseThrow();

        Integer last =
                repo.getLastQueueNumber(
                        a.getDoctorId(),
                        a.getDate()
                );

        a.setQueueNumber(last + 1);
        a.setCheckInTime(LocalDateTime.now());
        a.setStatus(AppointmentStatus.CHECKED_IN);
        a.setIsWalkIn(false);

        return repo.save(a);
    }
}