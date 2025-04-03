package org.intaehwang.chapter11.preserveWholeObject;

import lombok.Getter;

@Getter
public class Room {
    private final DayTempRange dayTempRange;

    public Room(DayTempRange dayTempRange) {
        this.dayTempRange = dayTempRange;
    }
}
