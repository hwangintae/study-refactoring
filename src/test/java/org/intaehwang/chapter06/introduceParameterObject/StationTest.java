package org.intaehwang.chapter06.introduceParameterObject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StationTest {

    @Test
    public void test() {

        // given
        Station station = new Station("ZB1", List.of(
                new Reading(47, LocalDateTime.of(2016, 11, 10, 9, 10)),
                new Reading(53, LocalDateTime.of(2016, 11, 10, 9, 20)),
                new Reading(58, LocalDateTime.of(2016, 11, 10, 9, 30)),
                new Reading(53, LocalDateTime.of(2016, 11, 10, 9, 40)),
                new Reading(51, LocalDateTime.of(2016, 11, 10, 9, 50))
        ));

        OperatingPlan operatingPlan = new OperatingPlan(50, 53);

        // when
        List<Reading> readings = Reading.readingsOutSideRange(station,
                operatingPlan.getNumberRange()
        );

        // then
        assertThat(readings)
                .extracting("temp")
                .containsExactly(47, 58);
    }

}