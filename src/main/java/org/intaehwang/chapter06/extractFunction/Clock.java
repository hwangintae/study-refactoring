package org.intaehwang.chapter06.extractFunction;

import java.time.LocalDate;

public class Clock implements ClockWrapper {
    private static final LocalDate now = LocalDate.now();

    @Override
    public LocalDate now() {
        return now;
    }

    public static LocalDate after30Days() {
        return now.plusDays(30);
    }
}
