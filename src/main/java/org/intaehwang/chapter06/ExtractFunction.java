package org.intaehwang.chapter06;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDate;

@Slf4j
public class ExtractFunction {
    public void printOwing(Invoice invoice) {
        int outstanding = 0;

        printBanner();

        for (Order o : invoice.getOrders()) {
            outstanding += o.getAmount();
        }

        recordDueDate(invoice);

        printDetails(invoice, outstanding);
    }

    public void printBanner() {
        log.info("*******************");
        log.info("**** 고객 채무 ****");
        log.info("*******************");
    }

    public void printDetails(Invoice invoice, int outstanding) {
        log.info("고객명: {}", invoice.getCustomer());
        log.info("채무액: {}", outstanding);
        log.info("마감일: {}", invoice.getDueDate());
    }

    public void recordDueDate(Invoice invoice) {
        LocalDate today = new Clock().now();
        invoice.setDueDate(today.plusDays(30));
    }
}
