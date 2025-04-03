package org.intaehwang.chapter11.replaceParameterWithQuery;

public class Order {
    private final int quantity;
    private final int itemPrice;

    public Order(int quantity, int itemPrice) {
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    public double finalPrice() {
        int basePrice = quantity * itemPrice;

        return this.discountedPrice(basePrice);
    }

    private double discountedPrice(int basePrice) {
        return switch (this.discountLevel()) {
            case 1 -> basePrice * 0.95;
            case 2 -> basePrice * 0.8;
            default -> throw new IllegalStateException("Unexpected value: " + this.discountLevel());
        };
    }

    private int discountLevel() {
        return (this.quantity > 100) ? 2 : 1;
    }
}
