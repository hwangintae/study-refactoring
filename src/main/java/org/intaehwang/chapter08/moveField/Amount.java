package org.intaehwang.chapter08.moveField;

public class Amount {
    private double amount;

    public Amount(double amount) {
        this.amount = amount;
    }

    public double subtract(double val) {
        return this.amount -= val;
    }

    public double multiply(double val) {
        return this.amount *= val;
    }
}
