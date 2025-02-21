package org.intaehwang.chapter06;

import java.time.LocalDate;

public class Clock implements ClockWrapper {

    @Override
    public LocalDate now() {
        return LocalDate.now();
    }
}
