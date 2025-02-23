package org.intaehwang.chapter06.extractVariable;

import org.intaehwang.chapter06.comm.Order;

public class ExtractVariable {
    public double price(Order order) {
        double basePrice = order.getQuantity() * order.getItemPrice();
        double quantityDiscount = Math.max(0, order.getQuantity() - 500) * order.getItemPrice() * 0.05;
        double shipping = Math.min(basePrice * 0.1, 100);

        return basePrice - quantityDiscount + shipping;
    }
}
