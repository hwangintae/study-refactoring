package org.intaehwang.chapter06.combineFunctionsIntoClass;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ReadingChapter10Test {

    @Test
    public void client1() {
        // given
        Reading aReading = new Reading("ivan", 10, 5, 2017);

        // when
        int baseCharge = aReading.baseCharge(aReading);

        assertThat(baseCharge).isEqualTo(100);
    }

    @Test
    public void client2() {
        // given
        Reading aReading = new Reading("ivan", 10, 5, 2017);

        // when
        int taxableCharge = aReading.taxableChargeFn(aReading);

        // then
        assertThat(taxableCharge).isEqualTo(60);
    }

    @Test
    public void client3() {
        // given
        Reading aReading = new Reading("ivan", 10, 5, 2017);

        // when
        int basicChargeAmount = aReading.baseCharge(aReading);

        // then
        assertThat(basicChargeAmount).isEqualTo(100);
    }


}