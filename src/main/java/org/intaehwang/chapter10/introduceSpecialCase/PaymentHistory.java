package org.intaehwang.chapter10.introduceSpecialCase;

import lombok.Getter;

@Getter
public class PaymentHistory {
    private final int weekDelinquentInLastYear;

    public PaymentHistory(int weekDelinquentInLastYear) {
        this.weekDelinquentInLastYear = weekDelinquentInLastYear;
    }
}
