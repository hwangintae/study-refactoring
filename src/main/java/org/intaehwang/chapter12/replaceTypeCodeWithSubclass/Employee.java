package org.intaehwang.chapter12.replaceTypeCodeWithSubclass;

public abstract class Employee {
    private final String name;

    public Employee(String name) {
        this.name = name;
    }

    public abstract String getType();

    public static Employee createEmployee(String type, String name) {
        return switch (type) {
            case "engineer" -> new Engineer(name);
            case "salesperson" -> new Salesperson(name);
            case "manager" -> new Manager(name);
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        };
    }
}
