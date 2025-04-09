package org.intaehwang.chapter12.extractSuperclass;

import lombok.Getter;

@Getter
public class Staff {
    private final int monthlyCost;

    public Staff(int monthlyCost) {
        this.monthlyCost = monthlyCost;
    }
}
