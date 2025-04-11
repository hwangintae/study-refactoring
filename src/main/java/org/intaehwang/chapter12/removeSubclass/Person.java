package org.intaehwang.chapter12.removeSubclass;

import lombok.Getter;

import java.util.List;

@Getter
public class Person {
    private final String name;
    private final String genderCode;

    public Person(String name, String genderCode) {
        this.name = name;
        this.genderCode = List.of("M", "F").contains(genderCode) ? genderCode : "X";
    }

    public static Person createPerson(People aRecord) {
        return switch (aRecord.gender()) {
            case "M" -> new Person(aRecord.name(), "M");
            case "F" -> new Person(aRecord.name(), "F");
            default -> new Person(aRecord.name(), "X");
        };
    }

    public String genderCode() {
        return this.genderCode;
    }

    public boolean isMale(Person aPerson) {
        return "M".equals(aPerson.genderCode());
    }
}
