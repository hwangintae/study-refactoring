package org.intaehwang.chapter06.inlineFunction;

import java.util.ArrayList;
import java.util.List;

public class InlineFunction {
    public int getRating(Driver driver) {
        return (driver.getNumberOfLateDeliveries() > 5) ? 2 : 1;
    }

    public List<List<String>> reportLines(Customer aCustomer) {
        List<List<String>> lines = new ArrayList<>();

        gatherCustomerData(lines, aCustomer);
        return lines;
    }

    public void gatherCustomerData(List<List<String>> out, Customer aCustomer) {
        out.add(List.of("name", aCustomer.getName()));
        out.add(List.of("location", aCustomer.getLocation()));
    }
}
