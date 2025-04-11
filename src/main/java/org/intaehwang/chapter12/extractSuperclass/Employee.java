package org.intaehwang.chapter12.extractSuperclass;

import lombok.Getter;

@Getter
public class Employee extends Party {
    private final Long id;
    private final int monthlyCost;

    public Employee(Long id, String name, int monthlyCost) {
        super(name);
        this.id = id;
        this.monthlyCost = monthlyCost;
    }

    @Override
    public int monthlyCost() {
        return monthlyCost;
    }
}
