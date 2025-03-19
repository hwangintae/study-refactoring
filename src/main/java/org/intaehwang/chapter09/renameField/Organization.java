package org.intaehwang.chapter09.renameField;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Organization {
    private String title;
    private String country;

    public Organization(String title, String country) {
        this.title = title;
        this.country = country;
    }
}
