package org.intaehwang.chapter06.extractFunction;

import lombok.Getter;

@Getter
public class Order {
    private final int amount;

    public Order(int amount) {
        this.amount = amount;
    }
}
