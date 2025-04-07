package org.intaehwang.chapter12.pullUpMethod;

public class Department extends Party {
    private final int monthlyCost;

    public Department(int monthlyCost) {
        super(monthlyCost);
        this.monthlyCost = monthlyCost;
    }
}
