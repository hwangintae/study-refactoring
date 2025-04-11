package org.intaehwang.chapter12.replaceSubclassWithDelegate;

import lombok.Getter;

@Getter
public class Show {
    private final double price;

    public Show(double price) {
        this.price = price;
    }

    public boolean hasOwnProperty(String str) {
        return true;
    }
}
