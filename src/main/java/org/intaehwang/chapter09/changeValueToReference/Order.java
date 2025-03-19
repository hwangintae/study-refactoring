package org.intaehwang.chapter09.changeValueToReference;

import lombok.Getter;

@Getter
public class Order {
    private final int number;
    private final Customer customer;

    public Order(int number, Customer customer) {
        this.number = number;
        this.customer = customer;
    }
}
