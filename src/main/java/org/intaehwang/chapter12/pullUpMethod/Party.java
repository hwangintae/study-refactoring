package org.intaehwang.chapter12.pullUpMethod;

public class Party {
    private final int monthlyCost;

    public Party(int monthlyCost) {
        this.monthlyCost = monthlyCost;
    }

    public int annualCost() {
        return this.monthlyCost * 12;
    }
}
