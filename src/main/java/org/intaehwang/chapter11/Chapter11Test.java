package org.intaehwang.chapter11;

import org.intaehwang.chapter11.parameterizeFunction.Person;
import org.intaehwang.chapter11.parameterizeFunction.Usd;
import org.intaehwang.chapter11.removeFlagArgument.Order;

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

    public void setOfAlarms() {
    }

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

    public static int deliveryDate(Order order, boolean isRush) {
        if (isRush) return rushDeliveryDate(order);
        else return regularDeliveryDate(order);
    }

    public static int rushDeliveryDate(Order order) {
        int deliveryTime;

        if (List.of("MA", "CT").contains(order.getDeliveryState())) deliveryTime = 1;
        else if (List.of("NY", "NH").contains(order.getDeliveryState())) deliveryTime = 2;
        else deliveryTime = 3;

        return order.getPlaceOn().plusDays(1 + deliveryTime);
    }

    public static int regularDeliveryDate(Order order) {
        int deliveryTime;

        if (List.of("MA", "CT", "NY").contains(order.getDeliveryState())) deliveryTime = 2;
        else if (List.of("ME", "NH").contains(order.getDeliveryState())) deliveryTime = 3;
        else deliveryTime = 4;

        return order.getPlaceOn().plusDays(2 + deliveryTime);
    }

}
