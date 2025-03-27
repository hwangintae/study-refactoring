package org.intaehwang.chapter10.introduceSpecialCase;

import lombok.Getter;

@Getter
public class NullPaymentHistory {
    private final int weekDelinquentInLastYear;

    public NullPaymentHistory(int weekDelinquentInLastYear) {
        this.weekDelinquentInLastYear = weekDelinquentInLastYear;
    }
}
