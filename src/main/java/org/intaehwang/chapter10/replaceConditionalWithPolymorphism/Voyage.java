package org.intaehwang.chapter10.replaceConditionalWithPolymorphism;

import lombok.Getter;

@Getter
public class Voyage {
    private final String zone;
    private final int length;

    public Voyage(String zone, int length) {
        this.zone = zone;
        this.length = length;
    }
}
