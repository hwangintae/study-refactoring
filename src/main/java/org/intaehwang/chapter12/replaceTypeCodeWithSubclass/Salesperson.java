package org.intaehwang.chapter12.replaceTypeCodeWithSubclass;

public class Salesperson extends Employee {
    public Salesperson(String name) {
        super(name);
    }

    public String getType() {
        return "salesperson";
    }
}
