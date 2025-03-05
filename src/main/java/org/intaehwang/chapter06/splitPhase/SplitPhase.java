package org.intaehwang.chapter06.splitPhase;

public class SplitPhase {
    public static double priceOrder(Product product, int quantity, ShippingMethod shippingMethod) {
        PriceDate priceDate = calculatePricingData(product, quantity);
        return applyShipping(priceDate , shippingMethod);
    }

    public static PriceDate calculatePricingData(Product product, int quantity) {
        double basePrice = product.basePrice() * quantity;
        double discount = Math.max(quantity - product.discountThreshold(), 0)
                * product.basePrice() * product.discountRate();

        return new PriceDate(basePrice, quantity, discount);
    }

    public static double applyShipping(PriceDate priceDate, ShippingMethod shippingMethod) {
        double shippingPerCase = (priceDate.basePrice() > shippingMethod.discountThreshold())
                ? shippingMethod.discountFee() : shippingMethod.feePerCase();
        double shippingCost = priceDate.quantity() * shippingPerCase;

        return priceDate.basePrice() - priceDate.discount() + shippingCost;
    }
}
