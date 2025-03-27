package org.intaehwang.chapter10.introduceSpecialCase2;

public class Site {
    private static final int REGISTRY_BILLING_PLANS_BASIC = 110;

    private final Customer customer;

    public Site(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return (customer == null) ? createUnknownCustomer() : this.customer;
    }

    public Customer createUnknownCustomer() {
        return new Customer("거주자", REGISTRY_BILLING_PLANS_BASIC, new PaymentHistory(0), true);
    }
}
