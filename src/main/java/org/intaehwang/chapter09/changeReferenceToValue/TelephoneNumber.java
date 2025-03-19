package org.intaehwang.chapter09.changeReferenceToValue;

import java.util.Objects;

public record TelephoneNumber(String areaCode, String number) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelephoneNumber that = (TelephoneNumber) o;
        return Objects.equals(number, that.number) && Objects.equals(areaCode, that.areaCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaCode, number);
    }
}
