package org.intaehwang.chapter06.inlineFunction;

import org.intaehwang.chapter06.comm.Customer;

import java.util.ArrayList;
import java.util.List;

public class InlineFunction {
    public int getRating(Driver driver) {
        return (driver.getNumberOfLateDeliveries() > 5) ? 2 : 1;
    }

    public List<List<String>> reportLines(Customer aCustomer) {
        List<List<String>> lines = new ArrayList<>();

        lines.add(List.of("name", aCustomer.getName()));
        lines.add(List.of("location", aCustomer.getLocation()));

        return lines;
    }
}
