package com.healthbox.hmsbackend.common.util;

import java.time.LocalTime;

public class TimeUtil {

    public static LocalTime addMinutes(LocalTime time, int minutes) {
        return time.plusMinutes(minutes);
    }
}