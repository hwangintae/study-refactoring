package org.intaehwang.chapter06.book;

import lombok.Getter;

import java.util.List;

@Getter
public class Book {
    private final List<String> reservations;

    public Book(List<String> reservations) {
        this.reservations = reservations;
    }

    public static Book of() {
        return new Book(List.of());
    }

    public void addReservation(String customer) {
        zz_addReservation(customer, false);
    }

    public void zz_addReservation(String customer, boolean isPriority) {
        this.reservations.add(customer);
    }

    public boolean inNewEngland(String state) {
        return List.of("MA", "CT", "ME", "VT", "NH", "RI")
                .contains(state);
    }
}
