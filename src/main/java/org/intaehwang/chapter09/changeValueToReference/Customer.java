package org.intaehwang.chapter09.changeValueToReference;

import lombok.Getter;

@Getter
public class Customer {
    private final String id;

    public Customer(String id) {
        this.id = id;
    }
}
