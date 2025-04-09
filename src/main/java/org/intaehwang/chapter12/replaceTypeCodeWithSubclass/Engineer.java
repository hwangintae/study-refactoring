package org.intaehwang.chapter12.replaceTypeCodeWithSubclass;

public class Engineer extends EmployeeType {

    public Engineer(String value) {
        super(value);
    }

    public String getType() {
        return "engineer";
    }
}
