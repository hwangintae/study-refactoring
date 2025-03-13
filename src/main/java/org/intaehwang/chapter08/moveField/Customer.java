package org.intaehwang.chapter08.moveField;

import lombok.Getter;

@Getter
public class Customer {
    private final String name;
    private double discountRate;
    private final CustomerContract customerContract;

    public Customer(String name, double discountRate) {
        this.name = name;
        setDiscountRate(discountRate);
        this.customerContract = new CustomerContract(Clock.dateToday());
    }

    private void setDiscountRate(double aNumber) {
        this.customerContract.setDiscountRate(aNumber);
    }

    public void becomePreferred() {
        setDiscountRate(this.discountRate + 0.03);
    }

    public double applyDiscount(Amount amount) {
        return amount.subtract(amount.multiply(this.discountRate));
    }
}
