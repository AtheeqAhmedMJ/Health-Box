package com.healthbox.hmsbackend.appointment.service;

import com.healthbox.hmsbackend.appointment.entity.*;
import com.healthbox.hmsbackend.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final AppointmentRepository repo;

    public Appointment callNext(Long doctorId){

        List<Appointment> queue =
                repo.getActiveQueue(
                        doctorId,
                        LocalDate.now()
                );

        for(Appointment a : queue){

            if(a.getStatus()==AppointmentStatus.CHECKED_IN){
                a.setStatus(AppointmentStatus.CONSULTING);
                return repo.save(a);
            }
        }

        throw new RuntimeException("Queue empty");
    }
}