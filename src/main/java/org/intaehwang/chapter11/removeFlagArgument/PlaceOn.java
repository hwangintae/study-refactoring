package org.intaehwang.chapter11.removeFlagArgument;

public class PlaceOn {
    private int day;

    public PlaceOn(int day) {
        this.day = day;
    }

    public int plusDays(int d) {
        return this.day + d;
    }
}
