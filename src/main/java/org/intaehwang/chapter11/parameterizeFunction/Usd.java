package org.intaehwang.chapter11.parameterizeFunction;

import lombok.Getter;

@Getter
public class Usd {
    private final double value;


    public Usd(double value) {
        this.value = value;
    }
}
