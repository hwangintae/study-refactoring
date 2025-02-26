package org.intaehwang.chapter06.extractVariable;

import org.intaehwang.chapter06.comm.Order;

public class ExtractVariable {
    public double price(Order order) {
        return order.getPrice();
    }
}
