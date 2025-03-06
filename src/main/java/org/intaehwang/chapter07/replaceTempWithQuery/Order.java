package org.intaehwang.chapter07.replaceTempWithQuery;

import lombok.Getter;

@Getter
public class Order {
    private final int quantity;
    private final Item item;

    public Order(int quantity, Item item) {
        this.quantity = quantity;
        this.item = item;
    }

    public double getPrice() {
        return getBasePrice() * getDiscountFactor();
    }

    private double getDiscountFactor() {
        double discountFactor = 0.98;

        if (getBasePrice() > 1_000) discountFactor -= 0.03;
        return discountFactor;
    }

    private double getBasePrice() {
        return this.quantity * this.item.getPrice();
    }
}
