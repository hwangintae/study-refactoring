package org.intaehwang.chapter12.replaceTypeCodeWithSubclass;

public class Manager extends EmployeeType {

    public Manager(String value) {
        super(value);
    }

    public String getType() {
        return "manager";
    }
}
