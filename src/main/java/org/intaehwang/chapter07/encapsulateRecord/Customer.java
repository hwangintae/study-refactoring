package org.intaehwang.chapter07.encapsulateRecord;

import java.util.HashMap;
import java.util.Map;

public class Customer {
    private String id;
    private String name;
    private Map<Integer, Map<Integer, Integer>> usages = new HashMap<>();

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setUsage(int year, int month, int amount) {
        if (!usages.containsKey(year)) {
            usages.put(year, new HashMap<>());
        }

        Map<Integer, Integer> integerIntegerMap = usages.get(year);

        integerIntegerMap.put(month, amount);
    }

    public int getUsage(int year, int month) {
        return usages.getOrDefault(year, new HashMap<>())
                .getOrDefault(month, 0);
    }
}
