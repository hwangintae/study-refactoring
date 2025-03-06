package org.intaehwang.chapter07.replacePrimitiveWithObject;

import lombok.Getter;

@Getter
public class Priority {
    private final String value;

    public Priority(String value) {
        this.value = value;
    }
}
