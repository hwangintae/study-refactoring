package org.intaehwang.chapter04;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Chapter04Test {

    @Test
    public void shortfailTest() {
        // given
        Province asia = Province.create(ProvinceData.getSample());

        // when
        int shortfall = asia.getShortfall();

        // then
        assertThat(shortfall).isEqualTo(5);
    }

    @Test
    public void profitTest() {
        // given
        Province asia = Province.create(ProvinceData.getSample());

        // when
        int profit = asia.getProfit();

        // then
        assertThat(profit).isEqualTo(230);
    }

    @Test
    public void changeProductionTest() {
        // given
        Province asia = Province.create(ProvinceData.getSample());
        asia.getProducers().get(0).setProduction("20");

        // when
        int shortfall = asia.getShortfall();
        int profit = asia.getProfit();

        // then
        assertThat(shortfall).isEqualTo(-6);
        assertThat(profit).isEqualTo(292);
    }

    @Test
    public void noProducersTest() {
        // given
        ProvinceData data = ProvinceData.builder()
                .name("No producers")
                .producers(new ArrayList<>())
                .demand(30)
                .price(20)
                .build();

        Province asia = Province.create(data);

        // when
        int shortfall = asia.getShortfall();
        int profit = asia.getProfit();

        // then
        assertThat(shortfall).isEqualTo(30);
        assertThat(profit).isEqualTo(0);
    }

    @Test
    public void zeroDemand() {
        // given
        Province asia = Province.create(ProvinceData.getSample());
        asia.setDemand("0");

        // when
        int shortfall = asia.getShortfall();
        int profit = asia.getProfit();

        // then
        assertThat(shortfall).isEqualTo(-25);
        assertThat(profit).isEqualTo(0);
    }

    @Test
    public void negativeDemand() {
        // given
        Province asia = Province.create(ProvinceData.getSample());
        asia.setDemand("-1");

        // when
        int shortfall = asia.getShortfall();
        int profit = asia.getProfit();

        // then
        assertThat(shortfall).isEqualTo(-26);
        assertThat(profit).isEqualTo(-10);
    }

    @Test
    public void emptyStringDemand() {
        // given
        Province asia = Province.create(ProvinceData.getSample());

        // expected
        assertThatThrownBy(() -> asia.setDemand(""))
                .isInstanceOf(NumberFormatException.class);
    }
}
