package org.intaehwang.chapter11.removeSettingMethod;

import lombok.Getter;

@Getter
public class Person {
    private String name;
    private String id;

    public Person(String id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }
}
