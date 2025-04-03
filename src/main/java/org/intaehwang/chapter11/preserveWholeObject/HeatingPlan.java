package org.intaehwang.chapter11.preserveWholeObject;

import lombok.Getter;

@Getter
public class HeatingPlan {
    private final TemperatureRange temperatureRange;

    public HeatingPlan(TemperatureRange temperatureRange) {
        this.temperatureRange = temperatureRange;
    }

    public boolean withinRange(DayTempRange dayTempRange) {

        return (dayTempRange.getLow() >= this.temperatureRange.getLow())
                && (dayTempRange.getHigh() <= this.temperatureRange.getHigh());
    }

    public boolean withinRange(int bottom, int top) {

        return (bottom >= this.temperatureRange.getLow())
                && (top <= this.temperatureRange.getHigh());
    }

    public boolean xxNEWwithinRange(DayTempRange tempRange) {
        int low = tempRange.getLow();
        int high = tempRange.getHigh();

        return this.withinRange(low, high);
    }
}
