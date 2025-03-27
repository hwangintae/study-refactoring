package org.intaehwang.chapter10.introduceSpecialCase;

public class UnknownCustomer implements MyCustomer {

    private static final int REGISTRY_BILLING_PLANS_BASIC = 110;

    public boolean isUnknown() {
        return true;
    }

    public String getName() {
        return "거주자";
    }

    public int getBillingPlan() {
        return REGISTRY_BILLING_PLANS_BASIC;
    }

    public void setBillingPlan(int billingPlan) {
    }

    public NullPaymentHistory getPaymentHistory() {
        return new NullPaymentHistory(0);
    }
}
