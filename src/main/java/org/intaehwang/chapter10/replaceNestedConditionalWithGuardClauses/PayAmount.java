package org.intaehwang.chapter10.replaceNestedConditionalWithGuardClauses;

public record PayAmount(
        int amount,
        String reasonCode
) {
}
