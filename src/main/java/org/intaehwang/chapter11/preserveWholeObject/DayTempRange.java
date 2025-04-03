package org.intaehwang.chapter11.preserveWholeObject;

import lombok.Getter;

@Getter
public class DayTempRange {
    private final int low;
    private final int high;

    public DayTempRange(int low, int high) {
        this.low = low;
        this.high = high;
    }
}
