package org.intaehwang.chapter04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Chapter04Test {

    private Province asia;

    @BeforeEach
    void setUp() {
        asia = Province.create(ProvinceData.getSample());
    }

    @Test
    public void shortfailTest() {
        // when
        int shortfall = asia.getShortfall();

        // then
        assertThat(shortfall).isEqualTo(5);
    }

    @Test
    public void profitTest() {
        // when
        int profit = asia.getProfit();

        // then
        assertThat(profit).isEqualTo(230);
    }
}
