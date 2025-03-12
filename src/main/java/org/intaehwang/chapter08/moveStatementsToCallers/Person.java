package org.intaehwang.chapter08.moveStatementsToCallers;

import lombok.Getter;

@Getter
public class Person {
    private final String name;
    private final Photo photo;

    public Person(String name, Photo photo) {
        this.name = name;
        this.photo = photo;
    }
}
