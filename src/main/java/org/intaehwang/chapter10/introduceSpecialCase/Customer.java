package org.intaehwang.chapter10.introduceSpecialCase;

import lombok.Getter;

@Getter
public class Customer implements MyCustomer {
    private final String name;
    private int billingPlan;
    private PaymentHistory paymentHistory;

    public Customer(String name, int billingPlan, PaymentHistory paymentHistory) {
        this.name = name;
        this.billingPlan = billingPlan;
        this.paymentHistory = paymentHistory;
    }

    public void setBillingPlan(int billingPlan) {
        this.billingPlan = billingPlan;
    }

    public void setPaymentHistory(PaymentHistory paymentHistory) {
        this.paymentHistory = paymentHistory;
    }

    public boolean isUnknown() {
        return false;
    }
}



