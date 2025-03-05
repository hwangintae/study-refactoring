package org.intaehwang.chapter07.encapsulateCollection;

import lombok.Getter;

@Getter
public class Course {
    private final String name;
    private final boolean advanced;

    public Course(String name, boolean advanced) {
        this.name = name;
        this.advanced = advanced;
    }
}
