package org.intaehwang.chapter12.extractSuperclass;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Department extends Party {
    private final List<Staff> staff;

    public Department(String name, List<Staff> staff) {
        super(name);
        this.staff = staff;
    }

    @Override
    public int monthlyCost() {
        return this.staff.stream()
                .map(Staff::getMonthlyCost)
                .reduce(0, Integer::sum);
    }

    public int headCount() {
        return this.staff.size();
    }
}
