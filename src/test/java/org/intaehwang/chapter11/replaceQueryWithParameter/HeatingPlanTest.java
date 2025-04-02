package org.intaehwang.chapter11.replaceQueryWithParameter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HeatingPlanTest {

    @Test
    public void heatingPlanTest() {
        // given
        int max = 28;
        int min = 21;
        int temperature = 24;

        HeatingPlan heatingPlan = new HeatingPlan(max, min, new Thermostat(temperature));

        // when
        int result = heatingPlan.targetTemperature();

        // then
        assertThat(result).isEqualTo(temperature);
    }

}