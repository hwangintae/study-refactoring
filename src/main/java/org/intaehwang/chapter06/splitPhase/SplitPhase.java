package org.intaehwang.chapter06.splitPhase;

public class SplitPhase {
    public static double priceOrder(Product product, int quantity, ShippingMethod shippingMethod) {
        double basePrice = product.basePrice() * quantity;
        double discount = Math.max(quantity - product.discountThreshold(), 0)
                * product.basePrice() * product.discountRate();

        PriceDate priceDate = new PriceDate(basePrice, quantity, discount);
        double price = applyShipping(priceDate , shippingMethod);

        return price;
    }

    public static double applyShipping(PriceDate priceDate, ShippingMethod shippingMethod) {
        double shippingPerCase = (priceDate.basePrice() > shippingMethod.discountThreshold())
                ? shippingMethod.discountFee() : shippingMethod.feePerCase();
        double shippingCost = priceDate.quantity() * shippingPerCase;

        return priceDate.basePrice() - priceDate.discount() + shippingCost;
    }
}
