package org.intaehwang.chapter07.hideDelegate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {
    private String name;
    private Department department;

    public String getManager() {
        return this.department.getManager();
    }
}
