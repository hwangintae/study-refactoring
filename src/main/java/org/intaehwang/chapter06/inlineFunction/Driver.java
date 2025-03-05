package org.intaehwang.chapter06.inlineFunction;

import lombok.Getter;

@Getter
public class Driver {

    private final int numberOfLateDeliveries;

    public Driver(int numberOfLateDeliveries) {
        this.numberOfLateDeliveries = numberOfLateDeliveries;
    }
}
