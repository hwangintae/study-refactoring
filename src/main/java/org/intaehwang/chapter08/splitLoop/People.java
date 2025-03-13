package org.intaehwang.chapter08.splitLoop;

import lombok.Getter;

@Getter
public class People {
    private final int age;
    private final int salary;

    public People(int age, int salary) {
        this.age = age;
        this.salary = salary;
    }
}
