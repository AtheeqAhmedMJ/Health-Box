package com.healthbox.hmsbackend.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DoctorScheduleDTO {

    private Long doctorId;
    private String doctorName;
    private List<TimeSlotDTO> slots;
}