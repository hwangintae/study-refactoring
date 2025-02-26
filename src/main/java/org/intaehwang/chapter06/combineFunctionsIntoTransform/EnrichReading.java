package org.intaehwang.chapter06.combineFunctionsIntoTransform;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EnrichReading {
    private final String customer;
    private final int quantity;
    private final int month;
    private final int year;
    private final int baseCharge;
    private final int taxableCharge;

    @Builder
    private EnrichReading(String customer, int quantity, int month, int year, int baseCharge, int taxableCharge) {
        this.customer = customer;
        this.quantity = quantity;
        this.month = month;
        this.year = year;
        this.baseCharge = baseCharge;
        this.taxableCharge = taxableCharge;
    }

    public static EnrichReading create(Reading aReading) {
        return EnrichReading.builder()
                .customer(aReading.customer())
                .quantity(aReading.quantity())
                .month(aReading.month())
                .year(aReading.year())
                .baseCharge(baseCharge(aReading))
                .taxableCharge(Math.max(0, baseCharge(aReading) - taxThreshold(aReading.year())))
                .build();
    }

    private static int baseCharge(Reading aReading) {
        return baseRate(aReading.month(), aReading.year()) * aReading.quantity();
    }

    private static int baseRate(int month, int year) {
        if (month >= 6 && month <= 8) { // 여름철
            return 12;
        } else if (month == 12 || month == 1 || month == 2) { // 겨울철
            return 15;
        }
        return 10; // 기본 요금
    }

    private static int taxThreshold(int month) {
        if (month >= 6 && month <= 8) {
            return 30;
        } else if (month == 12 || month == 1 || month == 2) {
            return 50;
        }
        return 40;
    }
}
