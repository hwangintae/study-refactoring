package org.intaehwang.chapter12.pullUpConstructorBody;

public class Manager extends Employee {
    private int grade;

    public Manager(String name, int grade) {
        super(name);
        this.grade = grade;
        finishConstruction();
    }
}
