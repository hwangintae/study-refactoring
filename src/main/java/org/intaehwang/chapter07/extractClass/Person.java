package org.intaehwang.chapter07.extractClass;

public class Person {
    private String name;

    private final TelephoneNumber telephoneNumber;

    public Person(String name, String officeAreaCode, String officeNumber) {
        this.name = name;
        this.telephoneNumber = new TelephoneNumber(officeAreaCode, officeNumber);
    }

    public String telephoneNumber() {
        return this.telephoneNumber.toString();
    }

    public String getName() {
        return this.name;
    }

    public String getOfficeAreaCode() {
        return this.telephoneNumber.getAreaCode();
    }

    public String getOfficeNumber() {
        return this.telephoneNumber.getNumber();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOfficeAreaCode(String officeAreaCode) {
        this.telephoneNumber.setAreaCode(officeAreaCode);
    }

    public void setOfficeNumber(String officeNumber) {
        this.telephoneNumber.setNumber(officeNumber);
    }
}
