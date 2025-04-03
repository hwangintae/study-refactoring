package org.intaehwang.chapter11;

import lombok.extern.slf4j.Slf4j;
import org.intaehwang.chapter11.parameterizeFunction.Person;
import org.intaehwang.chapter11.parameterizeFunction.Usd;
import org.intaehwang.chapter11.preserveWholeObject.DayTempRange;
import org.intaehwang.chapter11.preserveWholeObject.HeatingPlan;
import org.intaehwang.chapter11.preserveWholeObject.Room;
import org.intaehwang.chapter11.preserveWholeObject.TemperatureRange;
import org.intaehwang.chapter11.removeFlagArgument.Order;
import org.intaehwang.chapter11.replaceCommandWithFunction.ChargeCalculator;
import org.intaehwang.chapter11.replaceCommandWithFunction.Customer;
import org.intaehwang.chapter11.replaceCommandWithFunction.Provider;
import org.intaehwang.chapter11.replaceFunctionWithCommand.Candidate;
import org.intaehwang.chapter11.replaceFunctionWithCommand.MedicalExam;
import org.intaehwang.chapter11.replaceFunctionWithCommand.Scorer;
import org.intaehwang.chapter11.replaceFunctionWithCommand.ScoringGuide;

import java.util.List;

@Slf4j
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

    public static String checkWithinRange(Room room) {
        HeatingPlan aPlan = new HeatingPlan(new TemperatureRange(10, 20));

        if (!aPlan.withinRange(room.getDayTempRange()))
            return "방 온도가 지정 범위를 벗어났습니다.";

        return "";
    }

    public static String checkWithinRange2(Room room) {
        DayTempRange tempRange = room.getDayTempRange();
        HeatingPlan aPlan = new HeatingPlan(new TemperatureRange(10, 20));
        boolean isWithinRange = aPlan.xxNEWwithinRange(tempRange);

        if (!isWithinRange)
            return "방 온도가 지정 범위를 벗어났습니다.";

        return "";
    }

    public static int score(Candidate candidate, MedicalExam medicalExam, ScoringGuide scoringGuide) {
        return new Scorer(candidate, medicalExam, scoringGuide).execute();
    }

    public static int calculateCharge(Customer customer, int usage, Provider provider) {
        return charge(customer, usage, provider);
    }

    private static int charge(Customer customer, int usage, Provider provider) {
        return ChargeCalculator.charge(customer, usage, provider);
    }
}
