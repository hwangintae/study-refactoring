package org.intaehwang.chapter07.encapsulateRecord;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Organization {
    private final String name;
    private final String country;

    public Organization(String name, String country) {
        this.name = name;
        this.country = country;
    }
}
