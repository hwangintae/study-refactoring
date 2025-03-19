package org.intaehwang.chapter09.changeReferenceToValue;

public class Person {
    private TelephoneNumber telephoneNumber;

    public Person(TelephoneNumber telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getOfficeAreaCode() {
        return this.telephoneNumber.areaCode();
    }

    public void setOfficeAreaCode(String areaCode) {
        this.telephoneNumber = new TelephoneNumber(areaCode, this.telephoneNumber.number());
    }

    public String getOfficeNumber() {
        return this.telephoneNumber.number();
    }

    public void setOfficeNumber(String number) {
        this.telephoneNumber = new TelephoneNumber(this.telephoneNumber.areaCode(), number);
    }
}
