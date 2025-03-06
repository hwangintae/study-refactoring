package org.intaehwang.chapter07.encapsulateCollection;

import java.util.List;

public class Person {
    private final String name;
    private final List<Course> courses;

    public Person(String name, List<Course> courses) {
        this.name = name;
        this.courses = courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }
}
