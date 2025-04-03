package org.intaehwang.chapter11.replaceConstructorWithFactoryFunction;

import java.util.Map;

public class Employee {
    private final String name;
    private final String typeCode;

    private Employee(String name, String typeCode) {
        this.name = name;
        this.typeCode = typeCode;
    }

    public static Map<String, String> legalTypeCodes() {
        return Map.of(
                "E", "Engineer",
                "M", "Manager",
                "S", "Salesperson"
        );
    }

    public static Employee createEnginner(String name) {
        return new Employee(name, "E");
    }

    public static Employee createManager(String name) {
        return new Employee(name, "M");
    }

    public static Employee createSalesperson(String name) {
        return new Employee(name, "S");
    }

}
