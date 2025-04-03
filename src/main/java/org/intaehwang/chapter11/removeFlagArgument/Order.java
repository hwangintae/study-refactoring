package org.intaehwang.chapter11.removeFlagArgument;

import lombok.Getter;

@Getter
public class Order {
    private String deliveryState;
    private PlaceOn placeOn;

    public Order(String deliveryState, PlaceOn placeOn) {
        this.deliveryState = deliveryState;
        this.placeOn = placeOn;
    }
}
