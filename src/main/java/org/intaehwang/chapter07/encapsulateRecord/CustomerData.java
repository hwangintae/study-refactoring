package org.intaehwang.chapter07.encapsulateRecord;

import java.util.HashMap;
import java.util.Map;

public class CustomerData {
    private Map<String, Customer> customers = new HashMap<>();

    public Customer getCustomer(String customerID) {
        return customers.get(customerID);
    }

    public void addCustomer(String id, String name) {
        customers.put(id, new Customer(id, name));
    }
}
