package org.intaehwang.chapter06.splitPhase;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SplitPhaseChapter10Test {

    @Test
    public void test() {
        // given
        Product product = new Product(1_000.0, 5, 0.1);
        ShippingMethod shippingMethod = new ShippingMethod(10.0, 1000.0, 7.0);

        // when
        double result = SplitPhase.priceOrder(product, 100, shippingMethod);

        // then
        assertThat(result).isEqualTo(190500.0);
    }

}