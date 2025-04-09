package org.intaehwang.chapter12.replaceTypeCodeWithSubclass;

public class Manager extends Employee {
    public Manager(String name) {
        super(name);
    }

    public String getType() {
        return "manager";
    }
}
