package com.healthbox.hmsbackend.doctor.service;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SlotGenerationService {

    public List<LocalTime> generateSlots(
            LocalTime start,
            LocalTime end,
            int durationMinutes
    ) {

        List<LocalTime> slots = new ArrayList<>();

        LocalTime cursor = start;

        while (!cursor.plusMinutes(durationMinutes).isAfter(end)) {

            slots.add(cursor);
            cursor = cursor.plusMinutes(durationMinutes);
        }

        return slots;
    }
}