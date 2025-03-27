package org.intaehwang.chapter10.introduceSpecialCase2;

import lombok.Getter;
import lombok.Setter;
import org.intaehwang.chapter10.introduceSpecialCase.MyCustomer;

@Getter
@Setter
public class Customer {
    private String name;
    private int billingPlan;
    private PaymentHistory paymentHistory;
    private boolean unknown;

    public Customer(String name, int billingPlan, PaymentHistory paymentHistory, boolean unknown) {
        this.name = name;
        this.billingPlan = billingPlan;
        this.paymentHistory = paymentHistory;
        this.unknown = unknown;
    }
}



