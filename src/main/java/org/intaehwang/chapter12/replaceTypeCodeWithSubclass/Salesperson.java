package org.intaehwang.chapter12.replaceTypeCodeWithSubclass;

public class Salesperson extends EmployeeType {

    public Salesperson(String value) {
        super(value);
    }

    public String getType() {
        return "salesperson";
    }
}
