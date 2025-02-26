package org.intaehwang.chapter06.splitPhase;

public class SplitPhase {
    public static double priceOrder(Product product, int quantity, ShippingMethod shippingMethod) {
        double basePrice = product.basePrice() * quantity;
        double discount = Math.max(quantity - product.discountThreshold(), 0)
                * product.basePrice() * product.discountRate();
        double shippingPerCase = (basePrice > shippingMethod.discountThreshold())
                ? shippingMethod.discountFee() : shippingMethod.feePerCase();
        double shippingCost = quantity * shippingPerCase;
        double price = basePrice - discount + shippingCost;

        return price;
    }
}
