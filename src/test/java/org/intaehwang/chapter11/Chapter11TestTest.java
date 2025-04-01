package org.intaehwang.chapter11;

import org.intaehwang.chapter11.removeFlagArgument.Order;
import org.intaehwang.chapter11.removeFlagArgument.PlaceOn;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Chapter11TestTest {

    @Test
    public void deliveryDateTestMATure() {
        // given
        Order order = new Order("MA", new PlaceOn(2));

        // when
        int result = Chapter11Test.deliveryDate(order, true);

        // then
        assertThat(result).isEqualTo(4);
    }

    @Test
    public void deliveryDateTestNHTure() {
        // given
        Order order = new Order("NH", new PlaceOn(2));

        // when
        int result = Chapter11Test.deliveryDate(order, true);

        // then
        assertThat(result).isEqualTo(5);
    }

    @Test
    public void deliveryDateTestKOTure() {
        // given
        Order order = new Order("KO", new PlaceOn(2));

        // when
        int result = Chapter11Test.deliveryDate(order, true);

        // then
        assertThat(result).isEqualTo(6);
    }

    @Test
    public void deliveryDateTestMAFalse() {
        // given
        Order order = new Order("MA", new PlaceOn(2));

        // when
        int result = Chapter11Test.deliveryDate(order, false);

        // then
        assertThat(result).isEqualTo(6);
    }

    @Test
    public void deliveryDateTestNHFalse() {
        // given
        Order order = new Order("NH", new PlaceOn(2));

        // when
        int result = Chapter11Test.deliveryDate(order, false);

        // then
        assertThat(result).isEqualTo(7);
    }

    @Test
    public void deliveryDateTestKOFalse() {
        // given
        Order order = new Order("KO", new PlaceOn(2));

        // when
        int result = Chapter11Test.deliveryDate(order, false);

        // then
        assertThat(result).isEqualTo(8);
    }
}