package org.intaehwang.chapter10.introduceSpecialCase;

public class Site {
    private final Customer customer;

    public Site(Customer customer) {
        this.customer = customer;
    }

    public MyCustomer getCustomer() {
        return (customer == null) ? new UnknownCustomer() : this.customer;
    }
}
