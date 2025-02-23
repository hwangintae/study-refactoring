package org.intaehwang.chapter06.extractFunction;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public class ExtractFunction {
    public void printOwing(Invoice invoice) {

        printBanner();

        int outstanding = calculateOutstanding(invoice);

        recordDueDate(invoice);

        printDetails(invoice, outstanding);
    }

    public int calculateOutstanding(Invoice invoice) {
        List<Order> orders = invoice.getOrders();

        return orders.stream()
                .map(Order::getAmount)
                .reduce(0, Integer::sum);
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
