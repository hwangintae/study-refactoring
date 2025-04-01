package org.intaehwang.chapter11.preserveWholeObject;

import lombok.Getter;

@Getter
public class TemperatureRange {
    private final int low;
    private final int high;

    public TemperatureRange(int low, int high) {
        this.low = low;
        this.high = high;
    }
}
