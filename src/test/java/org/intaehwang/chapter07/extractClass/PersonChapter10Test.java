package org.intaehwang.chapter07.extractClass;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonChapter10Test {

    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person("대흥동 타이거 우직", "123", "4567890");
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("대흥동 타이거 우직", person.getName());
        assertEquals("123", person.getOfficeAreaCode());
        assertEquals("4567890", person.getOfficeNumber());
    }

    @Test
    void testTelephoneNumber() {
        assertEquals("(123)4567890", person.telephoneNumber());
    }

    @Test
    void testSetters() {
        person.setName("용현동 늑대 행복이");
        person.setOfficeAreaCode("987");
        person.setOfficeNumber("6543210");

        assertEquals("용현동 늑대 행복이", person.getName());
        assertEquals("987", person.getOfficeAreaCode());
        assertEquals("6543210", person.getOfficeNumber());
        assertEquals("(987)6543210", person.telephoneNumber());
    }
}