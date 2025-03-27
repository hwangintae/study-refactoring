package org.intaehwang.chapter06.extractFunction;

import org.intaehwang.chapter06.comm.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExtractFunctionChapter10Test {

    @Test
    public void printOwingTest() {
        // given
        ExtractFunction extractFunction = new ExtractFunction();
        List<Order> orders = List.of(
                Order.of(10),
                Order.of(20),
                Order.of(30)
        );

        Invoice invoice = new Invoice("황인태", orders);

        // when
        extractFunction.printOwing(invoice);

        // then
        assertThat(invoice.getDueDate())
                .isEqualTo(Clock.after30Days());
    }
}