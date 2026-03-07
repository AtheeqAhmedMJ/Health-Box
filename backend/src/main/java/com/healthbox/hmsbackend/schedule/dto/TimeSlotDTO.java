package com.healthbox.hmsbackend.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TimeSlotDTO {

    private String time;
    private String status; // FREE / BOOKED / BLOCKED
}