package org.intaehwang.chapter08.moveField;

import lombok.Getter;

@Getter
public class AccountType {
    private final String name;
    private final double interestRate;

    public AccountType(String name, double interestRate) {
        this.name = name;
        this.interestRate = interestRate;
    }
}
