package org.intaehwang.chapter08.moveField;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CustomerContract {
    private final LocalDate startDate;

    public CustomerContract(LocalDate startDate) {
        this.startDate = startDate;
    }
}
