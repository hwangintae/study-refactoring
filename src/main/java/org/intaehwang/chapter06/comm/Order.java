package org.intaehwang.chapter06.comm;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Order {
    private static final double SHIPPING_FEE_RATE = 0.1;
    private static final int MAX_SHIPPING_FEE = 100;
    private static final double DISCOUNT_RATE = 0.05;
    private static final int DISCOUNT_THRESHOLD = 500;

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

    public static Order of(double quantity) {
        return Order.builder()
                .quantity(quantity)
                .build();
    }

    public static Order of(double quantity, double itemPrice) {
        return Order.builder()
                .quantity(quantity)
                .itemPrice(itemPrice)
                .build();
    }

    public double getBasePrice() {
        return this.quantity * this.itemPrice;
    }
    public double getQuantityDiscount() {
        return Math.max(0, this.quantity - DISCOUNT_THRESHOLD) * this.itemPrice * DISCOUNT_RATE;
    }

    public double getShipping() {
        return Math.min(getBasePrice() * SHIPPING_FEE_RATE, MAX_SHIPPING_FEE);
    }

    public double getPrice() {
        return getBasePrice() - getQuantityDiscount() + getShipping();
    }

    public static double getDiscountRate() {
        return DISCOUNT_RATE;
    }

    public static int getDiscountThreshold() {
        return DISCOUNT_THRESHOLD;
    }

    public static int getMaxShippingFee() {
        return MAX_SHIPPING_FEE;
    }
}
