package org.intaehwang.chapter06.book;

import lombok.Getter;

import java.util.List;

@Getter
public class Book {
    private final List<String> reservations;

    public Book(List<String> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(String customer) {
        this.reservations.add(customer);
    }
}
