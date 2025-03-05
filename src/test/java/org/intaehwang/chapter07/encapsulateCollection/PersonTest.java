package org.intaehwang.chapter07.encapsulateCollection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class PersonTest {

    @Test
    public void test() {
        Person person = new Person("우직이", new ArrayList<>());

        List<Course> courses = person.getCourses();

        List<Course> list = courses.stream()
                .map(course -> new Course(course.getName(), false))
                .toList();
    }

}