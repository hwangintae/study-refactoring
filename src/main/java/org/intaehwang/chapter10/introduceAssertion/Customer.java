package org.intaehwang.chapter10.introduceAssertion;

import lombok.Getter;

@Getter
public class Customer {
    private int discountRate;

    public Customer(int discountRate) {
        this.discountRate = discountRate;
    }

    public int applyDiscount(int aNumber) {
        if (this.discountRate < 0) return aNumber;
        else return aNumber - (this.discountRate * aNumber);
    }

    public void setDiscountRate(int discountRate) {
        assert (discountRate >= 0);
        this.discountRate = discountRate;
    }
}
