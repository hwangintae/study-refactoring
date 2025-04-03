package org.intaehwang.chapter11.replcaeErrorCodeWithException;

public class ShippingRules {

    private final int role;

    public ShippingRules(int role) {
        this.role = role;
    }

    public int role() {
        return this.role;
    }
}
