package org.intaehwang.chapter12.extractSuperclass;

public abstract class Party {
    private final String name;

    public Party(String name) {
        this.name = name;
    }

    public abstract int monthlyCost();

    public int annualCost() {
        return this.monthlyCost() * 12;
    }
}
