package org.intaehwang.chapter06.introduceParameterObject;

import java.time.LocalDateTime;
import java.util.List;

public record Reading(int temp, LocalDateTime time) {

    public static List<Reading> readingsOutSideRange(Station station, OperatingPlan.NumberRange range) {
        return station.readings().stream()
                .filter(r -> r.temp() < range.min() || r.temp() > range.max())
                .toList();
    }
}