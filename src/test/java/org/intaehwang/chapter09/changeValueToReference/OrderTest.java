package org.intaehwang.chapter09.changeValueToReference;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class OrderTest {


    @Test
    public void orderTest() {
        // given
        Map<String, Customer> repository = new HashMap<>();
        repository.put("hwang", new Customer("hwang"));

        new Order(1, repository.get("hwang"));
        new Order(2, repository.get("hwang"));
        new Order(3, repository.get("hwang"));
        new Order(4, repository.get("hwang"));
        new Order(5, repository.get("hwang"));


    }
}