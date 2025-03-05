package org.intaehwang.chapter06.encapsulateVariable;

public class Owner {
    private Person person;

    public Owner(Person person) {
        this.person = person;
    }

    public Person getDefaultOwner() {
        return new Person(person.getFirstName(), person.getLastName());
    }

    public void setDefaultOwner(Person person) {
        this.person = person;
    }
}
