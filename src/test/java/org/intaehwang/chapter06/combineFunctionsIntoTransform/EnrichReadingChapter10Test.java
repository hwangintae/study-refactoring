package org.intaehwang.chapter06.combineFunctionsIntoTransform;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnrichReadingChapter10Test {
    @Test
    public void client1() {
        // given
        Reading aReading = new Reading("ivan", 10, 5, 2017);
        EnrichReading enrichReading = EnrichReading.create(aReading);

        // when
        int baseCharge = enrichReading.getBaseCharge();

        assertThat(baseCharge).isEqualTo(100);
    }

    @Test
    public void client2() {
        // given
        Reading aReading = new Reading("ivan", 10, 5, 2017);
        EnrichReading enrichReading = EnrichReading.create(aReading);

        // when
        int taxableCharge = enrichReading.getTaxableCharge();

        // then
        assertThat(taxableCharge).isEqualTo(60);
    }

    @Test
    public void client3() {
        // given
        Reading aReading = new Reading("ivan", 10, 5, 2017);
        EnrichReading enrichReading = EnrichReading.create(aReading);

        // when
        int basicChargeAmount = enrichReading.getBaseCharge();

        // then
        assertThat(basicChargeAmount).isEqualTo(100);


    }
}