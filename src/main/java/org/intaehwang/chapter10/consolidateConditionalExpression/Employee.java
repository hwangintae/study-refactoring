package org.intaehwang.chapter10.consolidateConditionalExpression;

public record Employee(
        int seniority,
        int monthsDisabled,
        boolean isPartTime,
        boolean onVacation,
        boolean isSeparated,
        boolean isRetired
) {
}
