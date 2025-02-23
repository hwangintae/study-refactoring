package org.intaehwang.chapter06.extractVariable;

import org.intaehwang.chapter06.comm.Order;

public class ExtractVariable {
    public double price(Order order) {
        return order.getQuantity() * order.getItemPrice() -
                Math.max(0, order.getQuantity() - 500) * order.getItemPrice() * 0.05 +
                Math.min(order.getQuantity() * order.getItemPrice() * 0.1, 100);
    }
}
