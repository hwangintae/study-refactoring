package org.intaehwang.chapter07.extractClass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelephoneNumber {
    private String areaCode;
    private String number;

    public TelephoneNumber(String areaCode, String number) {
        this.areaCode = areaCode;
        this.number = number;
    }

    public String toString() {
        return "(" + this.areaCode + ")" + this.number;
    }
}
