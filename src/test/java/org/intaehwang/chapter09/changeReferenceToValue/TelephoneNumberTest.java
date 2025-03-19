package org.intaehwang.chapter09.changeReferenceToValue;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TelephoneNumberTest {

    @Test
    public void isEquals() {
        TelephoneNumber t1 = new TelephoneNumber("312", "555-0142");
        TelephoneNumber t2 = new TelephoneNumber("312", "555-0142");

        assertThat(t1.equals(t2)).isTrue();
    }
}