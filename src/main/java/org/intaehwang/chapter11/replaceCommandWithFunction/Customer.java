package org.intaehwang.chapter11.replaceCommandWithFunction;

import lombok.Getter;

@Getter
public class Customer {
    private final int baseRate;

    public Customer(int baseRate) {
        this.baseRate = baseRate;
    }
}
