package org.intaehwang.chapter06.comm;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Order {
    private final int amount;
    private final double quantity;
    private final double itemPrice;

    @Builder
    public Order(int amount, double quantity, double itemPrice) {
        this.amount = amount;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    public static Order of(int amount) {
        return Order.builder()
                .amount(amount)
                .build();
    }

    public double getBasePrice() {
        return this.quantity * this.itemPrice;
    }
    public double getQuantityDiscount() {
        return Math.max(0, this.quantity - 500) * this.itemPrice * 0.05;
    }

    public double getShipping() {
        return Math.min(getBasePrice() * 0.1, 100);
    }

    public double getPrice() {
        return getBasePrice() - getQuantityDiscount() + getShipping();
    }
}
