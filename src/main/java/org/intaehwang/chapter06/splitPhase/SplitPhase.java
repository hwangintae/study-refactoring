package org.intaehwang.chapter06.splitPhase;

public class SplitPhase {
    public static double priceOrder(Product product, int quantity, ShippingMethod shippingMethod) {
        double basePrice = product.basePrice() * quantity;
        double discount = Math.max(quantity - product.discountThreshold(), 0)
                * product.basePrice() * product.discountRate();
        double price = applyShipping(basePrice, shippingMethod, quantity, discount);

        return price;
    }

    public static double applyShipping(double basePrice, ShippingMethod shippingMethod, int quantity, double discount) {
        double shippingPerCase = (basePrice > shippingMethod.discountThreshold())
                ? shippingMethod.discountFee() : shippingMethod.feePerCase();
        double shippingCost = quantity * shippingPerCase;

        return basePrice - discount + shippingCost;
    }
}
