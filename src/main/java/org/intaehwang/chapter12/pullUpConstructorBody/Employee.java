package org.intaehwang.chapter12.pullUpConstructorBody;

public class Employee extends Party {
    private Long id;
    private String name;
    private int monthlyCost;

    public Employee(String name) {
        super(name);
        this.name = name;
    }

    public boolean isPrivileged() {
        return false;
    }

    public void assignCar() {}

    public void finishConstruction() {
        if (this.isPrivileged()) this.assignCar();
    }
}
