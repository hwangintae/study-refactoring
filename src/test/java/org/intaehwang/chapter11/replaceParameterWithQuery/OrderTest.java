package org.intaehwang.chapter11.replaceParameterWithQuery;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @Test
    public void orderTest() {
        // given
        Order order = new Order(1000, 1000);

        // when
        double result = order.finalPrice();

        // then
        assertThat(result).isEqualTo(800000.0);
    }

    @Test
    public void orderTestUnder100() {
        // given
        Order order = new Order(10, 1000);

        // when
        double result = order.finalPrice();

        // then
        assertThat(result).isEqualTo(9500.0);
    }

}