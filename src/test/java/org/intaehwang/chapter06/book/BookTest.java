package org.intaehwang.chapter06.book;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.intaehwang.chapter06.comm.Address;
import org.intaehwang.chapter06.comm.Customer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.*;

class BookTest {

    @Test
    public void inNewEnglandTest() {
        // given
        Book book = Book.of();

        List<Customer> someCustomers = List.of(
                new Customer("고", "대흥동", new Address("MA")),
                new Customer("양", "타이거", new Address("CT")),
                new Customer("이", "우직", new Address("ME"))
        );

        // when
        List<Customer> result = someCustomers.stream()
                .filter(book::inNewEngland)
                .toList();

        // then
        assertThat(result)
                .extracting("name", "location", "address")
                .containsExactly(
                        tuple("고", "대흥동", new Address("MA")),
                        tuple("양", "타이거", new Address("CT")),
                        tuple("이", "우직", new Address("ME"))
                );
    }

}