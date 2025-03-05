package org.intaehwang.chapter07.extractClass;

public class Person {
    private String name;
    private String officeAreaCode;
    private String officeNumber;

    public Person(String name, String officeAreaCode, String officeNumber) {
        this.name = name;
        this.officeAreaCode = officeAreaCode;
        this.officeNumber = officeNumber;
    }

    public String telephoneNumber() {
        return "(" + officeAreaCode + ")" + officeNumber;
    }

    public String getName() {
        return this.name;
    }

    public String getOfficeAreaCode() {
        return this.officeAreaCode;
    }

    public String getOfficeNumber() {
        return this.officeNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOfficeAreaCode(String officeAreaCode) {
        this.officeAreaCode = officeAreaCode;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

}
