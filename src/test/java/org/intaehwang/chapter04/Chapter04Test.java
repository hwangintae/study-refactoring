package org.intaehwang.chapter04;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class Chapter04Test {

    @Test
    public void shortfailTest() {
        // given
        Province asia = Province.create(ProvinceData.getSample());

        // when
        int shortfall = asia.getShortfall();

        // then
        // junit의 Assertions 사용
        assertEquals(5, shortfall);

        // assertj의 Assertions 사용
        assertThat(shortfall).isEqualTo(5);
    }
}
