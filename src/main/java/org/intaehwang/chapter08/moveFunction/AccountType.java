package org.intaehwang.chapter08.moveFunction;

import lombok.Getter;

@Getter
public class AccountType {
    private final boolean premium;

    public AccountType(boolean premium) {
        this.premium = premium;
    }

    public double overdraftCharge(Account account) {
        if (this.premium) {
            double baseCharge = 10;

            if (account.getDaysOverdrawn() <= 7) return baseCharge;
            else return baseCharge + (account.getDaysOverdrawn() - 7) * 0.85;
        } else return account.getDaysOverdrawn() * 1.75;
    }
}
