package org.intaehwang.chapter11.replcaeErrorCodeWithException;

public class CountryData {

    public static int shippingRules(String country) {

        if ("ko".equals(country)) return 9999;
        return -9999;
    }
}