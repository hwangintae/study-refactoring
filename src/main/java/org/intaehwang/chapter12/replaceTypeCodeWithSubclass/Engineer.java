package org.intaehwang.chapter12.replaceTypeCodeWithSubclass;

public class Engineer extends Employee {
    public Engineer(String name) {
        super(name);
    }

    public String getType() {
        return "engineer";
    }
}
