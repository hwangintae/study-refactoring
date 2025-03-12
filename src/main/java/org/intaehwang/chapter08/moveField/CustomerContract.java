package org.intaehwang.chapter08.moveField;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerContract {
    private final LocalDate startDate;
    private double discountRate;

    public CustomerContract(LocalDate startDate) {
        this.startDate = startDate;
    }

    public CustomerContract(LocalDate startDate, double discountRate) {
        this.startDate = startDate;
        this.discountRate = discountRate;
    }
}
