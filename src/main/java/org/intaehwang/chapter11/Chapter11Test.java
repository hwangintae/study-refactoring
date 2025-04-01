package org.intaehwang.chapter11;

import org.intaehwang.chapter11.parameterizeFunction.Person;
import org.intaehwang.chapter11.parameterizeFunction.Usd;

import java.util.List;

public class Chapter11Test {

    public void alertForMiscreant(List<String> people) {
        for (String p : people) {
            if ("조커".equals(p)) {
                setOfAlarms();
                return;
            }
            if ("사루만".equals(p)) {
                setOfAlarms();
                return;
            }
        }
    }

    public String findMiscreant(List<String> people) {
        for (String p : people) {
            if ("조커".equals(p)) {
                return "조커";
            }
            if ("사루만".equals(p)) {
                return "사루만";
            }
        }

        return "";
    }

    public void setOfAlarms() {}

    public void tenPercentRaise(Person person) {
        person.setSalary(person.getSalary().multiply(1.1));
    }

    public void fivePercentRaise(Person person) {
        person.setSalary(person.getSalary().multiply(1.05));
    }

    public void raise(Person person, double factor) {
        person.setSalary(person.getSalary().multiply(1 + factor));
    }

    public Usd baseCharge(int usage) {
        if (usage < 0) return new Usd(0);

        double amount = withinBand(usage, 0, 100) * 0.03
                + withinBand(usage, 100, 200) * 0.05
                + withinBand(usage, 200, Integer.MAX_VALUE) * 0.07;

        return new Usd(amount);
    }

    public int withinBand(int usage, int bottom, int top) {
        return usage > bottom ? Math.min(usage, top) - bottom : 0;
    }

}
