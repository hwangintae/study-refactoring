package org.intaehwang.chapter11.removeSettingMethod;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersonTest {

    @Test
    public void personTest() {
        // given
        Person martin = new Person("1234");


        // when
        martin.setName("마틴");

        // then
        assertThat(martin).extracting("name", "id")
                .contains("마틴", "1234");
    }

}