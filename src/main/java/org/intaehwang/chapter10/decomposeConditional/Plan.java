package org.intaehwang.chapter10.decomposeConditional;

import java.time.LocalDate;

public record Plan(
        LocalDate summerStart,
        LocalDate summerEnd,
        double summerRate,
        int regularServiceCharge
) {

}
