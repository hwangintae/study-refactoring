package org.intaehwang.chapter09.replaceDerivedVariableWithQuery;

import lombok.Getter;

@Getter
public class Adjustment {
    private final int amount;

    public Adjustment(int amount) {
        this.amount = amount;
    }
}
