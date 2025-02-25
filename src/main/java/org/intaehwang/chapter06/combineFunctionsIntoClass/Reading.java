package org.intaehwang.chapter06.combineFunctionsIntoClass;

/**
 * @param customer customer: "ivan", quantity: 10, month: 5, year: 2017
 */
public record Reading(String customer, int quantity, int month, int year) {

    public int baseCharge(Reading aReading) {
        return baseRate(aReading.month(), aReading.year()) * aReading.quantity();
    }

    public int baseRate(int month, int year) {
        if (month >= 6 && month <= 8) { // 여름철
            return 12;
        } else if (month == 12 || month == 1 || month == 2) { // 겨울철
            return 15;
        }
        return 10; // 기본 요금
    }

    public int taxThreshold(int month) {
        if (month >= 6 && month <= 8) {
            return 30;
        } else if (month == 12 || month == 1 || month == 2) {
            return 50;
        }
        return 40;
    }

    public int taxableChargeFn(Reading aReading) {
        return Math.max(0, aReading.baseCharge(aReading) - taxThreshold(aReading.year()));
    }
}
