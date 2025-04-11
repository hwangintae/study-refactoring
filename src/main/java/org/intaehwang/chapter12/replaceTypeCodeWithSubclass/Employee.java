package org.intaehwang.chapter12.replaceTypeCodeWithSubclass;

import java.util.List;

public class Employee {
    private final String name;
    private EmployeeType type;

    public Employee(String name, EmployeeType type) {
        this.name = name;
        this.type = type;
    }

    public static EmployeeType createEmployeeType(String aString) {
        return switch (aString) {
            case "engineer" -> new Engineer(aString);
            case "Manager" -> new Manager(aString);
            case "salesperson" -> new Salesperson(aString);
            default -> throw new IllegalArgumentException("Unknown employee type: " + aString);
        };
    }

    private void validateType(String arg) {
        if (!List.of("engineer", "manager", "salesperson").contains(arg)) {
            throw new IllegalArgumentException("Invalid type " + arg);
        }
    }

    public String typeString() {
        return this.type.toString();
    }

    public EmployeeType getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = Employee.createEmployeeType(type);
    }

    public String capitalizedType() {
        return Character.toUpperCase(this.type.toString().charAt(0))
                + this.type.toString().substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return this.name + "(" + this.type.capitalizedName() + ")";
    }
}
