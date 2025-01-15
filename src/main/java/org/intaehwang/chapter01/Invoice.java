package org.intaehwang.chapter01;

import java.util.List;

public record Invoice(String customer, List<Performance> performances) {
    public Invoice {
        if (customer == null) {
            throw new IllegalArgumentException("customer cannot be null");
        }

        if (performances == null || performances.isEmpty()) {
            throw new IllegalArgumentException("performances cannot be null or empty");
        }

    }
}