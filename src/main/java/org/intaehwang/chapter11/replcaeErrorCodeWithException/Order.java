package org.intaehwang.chapter11.replcaeErrorCodeWithException;

import lombok.Getter;

@Getter
public class Order {
    private final String country;

    public Order(String country) {
        this.country = country;
    }
}
