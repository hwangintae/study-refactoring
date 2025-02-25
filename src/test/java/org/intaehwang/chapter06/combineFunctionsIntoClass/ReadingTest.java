package org.intaehwang.chapter06.combineFunctionsIntoClass;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ReadingTest {

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
        int taxableCharge = Math.max(0, aReading.baseCharge(aReading) - taxThreshold(aReading.year()));

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

    public int taxThreshold(int month) {
        if (month >= 6 && month <= 8) {
            return 30;
        } else if (month == 12 || month == 1 || month == 2) {
            return 50;
        }
        return 40;
    }
}