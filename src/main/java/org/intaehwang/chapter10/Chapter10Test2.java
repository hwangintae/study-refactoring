package org.intaehwang.chapter10;

import org.intaehwang.chapter10.introduceSpecialCase2.Customer;

public class Chapter10Test2 {
    private static final int NEW_PLAN = 110;

    public void client1(Customer customer) {
        String customerName = customer.getName();
    }

    public void client2(Customer customer) {
        int plan = customer.getBillingPlan();
    }

    public void client3(Customer customer) {
        customer.setBillingPlan(NEW_PLAN);
    }

    public void client4(Customer customer) {
        int weeksDelinquent = customer.getPaymentHistory().getWeekDelinquentInLastYear();
    }
}
