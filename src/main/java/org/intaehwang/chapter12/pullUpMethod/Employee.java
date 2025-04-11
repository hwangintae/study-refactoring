package org.intaehwang.chapter12.pullUpMethod;

public class Employee extends Party {
    private final int monthlyCost;

    public Employee(int monthlyCost) {
        super(monthlyCost);
        this.monthlyCost = monthlyCost;
    }
}
