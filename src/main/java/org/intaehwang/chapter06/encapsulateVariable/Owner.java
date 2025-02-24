package org.intaehwang.chapter06.encapsulateVariable;

public class Owner {
    private Person person;

    public Owner(Person person) {
        this.person = person;
    }

    public Person getDefaultOwner() {
        return person;
    }

    public void setDefaultOwner(Person person) {
        this.person = person;
    }
}
