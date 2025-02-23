package org.intaehwang.chapter06.extractFunction;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class Invoice {
    private final String customer;
    private List<Order> orders;
    private LocalDate dueDate;

    public Invoice(String customer, List<Order> orders) {
        this.customer = customer;
        this.orders = orders;
    }


}
