package org.intaehwang.chapter08.moveFunction;

import lombok.Getter;

@Getter
public class Account {

    private int daysOverdrawn;
    private AccountType type;

    public double bankCharge() {
        double result = 4.5;

        if (daysOverdrawn > 0) result += type.overdraftCharge(this);

        return result;
    }
}
