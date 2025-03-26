package org.intaehwang.chapter10.replaceNestedConditionalWithGuardClauses;

public record Instrument(
        int capital,
        int interestRate,
        int duration,
        int income,
        int adjustmentFactor
) {
}
