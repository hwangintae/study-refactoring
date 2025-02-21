package org.intaehwang.chapter06;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDate;

@Slf4j
public class ExtractFunction {
    public void printOwing(Invoice invoice) {
        int outstanding = 0;

        log.info("*******************");
        log.info("**** 고객 채무 ****");
        log.info("*******************");

        for (Order o : invoice.getOrders()) {
            outstanding += o.getAmount();
        }

        LocalDate today = new Clock().now();
        invoice.setDueDate(today.plusDays(30));

        log.info("고객명: {}", invoice.getCustomer());
        log.info("채무액: {}", outstanding);
        log.info("마감일: {}", invoice.getDueDate());
    }
}
