package org.intaehwang.chapter06.comm;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Customer {
    public final String name;
    public final String location;
    public final Address address;

    @Builder
    public Customer(String name, String location, Address address) {
        this.name = name;
        this.location = location;
        this.address = address;
    }

    public static Customer of(String name, String location) {
        return Customer.builder()
                .name(name)
                .location(location)
                .build();
    }
}
