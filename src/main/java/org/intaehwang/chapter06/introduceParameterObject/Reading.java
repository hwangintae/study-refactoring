package org.intaehwang.chapter06.introduceParameterObject;

import java.time.LocalDateTime;
import java.util.List;

public record Reading(int temp, LocalDateTime time) {

    public static List<Reading> readingsOutSideRange(Station station, int min, int max) {
        return station.readings().stream()
                .filter(r -> r.temp() < min || r.temp() > max)
                .toList();
    }
}