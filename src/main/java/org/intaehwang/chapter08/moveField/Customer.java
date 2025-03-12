package org.intaehwang.chapter08.moveField;

import lombok.Getter;

@Getter
public class Customer {
    private final String name;
    private double discountRate;
    private final CustomerContract customerContract;

    public Customer(String name, double discountRate) {
        this.name = name;
        this.discountRate = discountRate;
        this.customerContract = new CustomerContract(Clock.dateToday());
    }

    public void becomePreferred() {
        this.discountRate += 0.03;
    }

    public double applyDiscount(Amount amount) {
        return amount.subtract(amount.multiply(this.discountRate));
    }
}
