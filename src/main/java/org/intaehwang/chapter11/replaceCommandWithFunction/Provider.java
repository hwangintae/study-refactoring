package org.intaehwang.chapter11.replaceCommandWithFunction;

import lombok.Getter;

@Getter
public class Provider {
    private final int connectionCharge;

    public Provider(int connectionCharge) {
        this.connectionCharge = connectionCharge;
    }
}
