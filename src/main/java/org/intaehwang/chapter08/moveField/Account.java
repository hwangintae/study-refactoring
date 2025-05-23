package org.intaehwang.chapter08.moveField;

import lombok.Getter;

@Getter
public class Account {
    private final int number;
    private final AccountType type;
    private final double interestRate;

    public Account(int number, AccountType type, double interestRate) {
        this.number = number;
        this.type = type;

        if (interestRate != type.getInterestRate()) {
            throw new IllegalArgumentException("Interest rate must be equal to " + type.getInterestRate());
        }
        this.interestRate = interestRate;
    }
}
