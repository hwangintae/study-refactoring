package org.intaehwang.chapter10.replaceConditionalWithPolymorphism;

import lombok.Getter;

@Getter
public class History {
    private final String zone;
    private final int profit;

    public History(String zone, int profit) {
        this.zone = zone;
        this.profit = profit;
    }
}