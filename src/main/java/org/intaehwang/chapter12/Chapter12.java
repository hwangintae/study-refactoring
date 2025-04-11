package org.intaehwang.chapter12;

import org.intaehwang.chapter12.extractSuperclass.Employee;
import org.intaehwang.chapter12.removeSubclass.People;
import org.intaehwang.chapter12.removeSubclass.Person;

import java.util.List;

public class Chapter12 {

    public List<Person> loadFromInput(List<People> people) {
        return people.stream()
                .map(Person::createPerson)
                .toList();
    }
}
