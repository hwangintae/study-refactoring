package org.intaehwang.chapter11.parameterizeFunction;

import lombok.Getter;

@Getter
public class Salary {
    private double value;

    public Salary(double value) {
        this.value = value;
    }

    public Salary multiply(double d) {
        return new Salary(this.value * d);
    }

}
