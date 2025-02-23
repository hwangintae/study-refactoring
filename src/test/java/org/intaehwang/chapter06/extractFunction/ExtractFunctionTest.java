package org.intaehwang.chapter06.extractFunction;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExtractFunctionTest {

    @Test
    public void printOwingTest() {
        // given
        ExtractFunction extractFunction = new ExtractFunction();
        List<Order> orders = List.of(
                new Order(10),
                new Order(20),
                new Order(30)
        );

        Invoice invoice = new Invoice("황인태", orders);

        // when
        extractFunction.printOwing(invoice);

        // then
        assertThat(invoice.getDueDate())
                .isEqualTo(Clock.after30Days());
    }
}