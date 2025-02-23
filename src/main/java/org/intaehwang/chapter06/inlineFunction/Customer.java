package org.intaehwang.chapter06.inlineFunction;

import lombok.Getter;

@Getter
public class Customer {
    public final String name;
    public final String location;

    public Customer(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
