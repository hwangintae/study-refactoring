package org.intaehwang.chapter12.pullUpConstructorBody;

public class Department extends Party {
    private String name;
    private String staff;

    public Department(String name) {
        super(name);
        this.name = name;
    }
}
