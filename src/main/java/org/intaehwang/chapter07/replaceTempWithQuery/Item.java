package org.intaehwang.chapter07.replaceTempWithQuery;

import lombok.Getter;

@Getter
public class Item {
    private final int price;

    public Item(int price) {
        this.price = price;
    }
}
