package org.intaehwang.chapter08.moveStatementsToCallers;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Photo {
    private final String title;
    private final String location;
    private final LocalDate date;

    public Photo(String title, String location, LocalDate date) {
        this.title = title;
        this.location = location;
        this.date = date;
    }
}
