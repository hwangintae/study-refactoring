package org.intaehwang.chapter10;

import org.intaehwang.chapter10.consolidateConditionalExpression.Employee;
import org.intaehwang.chapter10.decomposeConditional.Plan;
import org.intaehwang.chapter10.introduceSpecialCase.Customer;
import org.intaehwang.chapter10.introduceSpecialCase.UnknownCustomer;
import org.intaehwang.chapter10.replaceConditionalWithPolymorphism.*;
import org.intaehwang.chapter10.replaceNestedConditionalWithGuardClauses.Instrument;
import org.intaehwang.chapter10.replaceNestedConditionalWithGuardClauses.PayAmount;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Chapter10Test {
    private static final int NEW_PLAN = 110;

    public Chapter10Test() {
    }

    public void test1(Plan plan, int quantity) {
        double charge;

        LocalDate aDate = LocalDate.now();

        if (summer(plan, aDate)) {
            charge = summerCharge(plan, quantity);
        } else {
            charge = regularCharge(plan, quantity);
        }
    }

    public double regularCharge(Plan plan, int quantity) {
        return quantity * plan.summerRate() + plan.regularServiceCharge();
    }

    public double summerCharge(Plan plan, int quantity) {
        return quantity * plan.summerRate();
    }

    public boolean summer(Plan plan, LocalDate aDate) {
        return !aDate.isBefore(plan.summerStart()) && !aDate.isAfter(plan.summerEnd());
    }

    public int disabilityAmount(Employee anEmployee) {
        if (isNotEligibleForDisability(anEmployee)) return 0;
        if (anEmployee.onVacation()
                && (anEmployee.seniority() > 10)) {
            return 1;
        }

        return 2;
    }

    public boolean isNotEligibleForDisability(Employee anEmployee) {
        return (anEmployee.seniority() < 2)
                || (anEmployee.monthsDisabled() > 12)
                || (anEmployee.isPartTime());
    }

    public PayAmount payAmount(Employee anEmployee) {
        if (anEmployee.isSeparated()) return new PayAmount(0, "SEP");

        if (anEmployee.isRetired()) return new PayAmount(0, "RET");
//        lorem.ipsum(dolor.sitAmet);
//        consectetur(adipiscing).elit();
//        sed.do.eiusmod = tempor.indicicunt.ut(labore) && dolore(magna.aliqua);
//        ut.enim.ad(minim.weniam);
        return someFinalComputation();
    }

    public PayAmount someFinalComputation() {
        return new PayAmount(0, "");
    }

    public int adjustedCapital(Instrument anInstrument) {
        if (anInstrument.capital() <= 0
                || (anInstrument.interestRate() <= 0 || anInstrument.duration() <= 0)) return 0;

        return (anInstrument.income() / anInstrument.duration()) * anInstrument.adjustmentFactor();
    }

    public Map<String, String> plumages(List<Bird> birds) {
        return birds.stream()
                .collect(Collectors.toMap(Bird::getName, Bird::plumage));
    }

    public Map<String, Integer> speeds(List<Bird> birds) {
        return birds.stream()
                .collect(Collectors.toMap(Bird::getName, Bird::airSpeedVelocity));
    }

    public String rating(Voyage voyage, List<History> histories) {
        return createRating(voyage, histories).value();
    }

    public Rating createRating(Voyage voyage, List<History> histories) {
        if ("중국".equals(voyage.getZone()) && histories.stream()
                .anyMatch(v -> "중국".equals(v.getZone()))) {
            return new ExperiencedChinaRating(voyage, histories);
        } else return new Rating(voyage, histories);
    }

    public void client1(Customer customer) {
        String customerName = customer.getName();
    }

    public void client2(Customer customer) {
        int plan = customer.getBillingPlan();
    }

    public void client3(Customer customer) {
        customer.setBillingPlan(NEW_PLAN);
    }

    public void client4(Customer customer) {
        int weeksDelinquent = customer.getPaymentHistory().getWeekDelinquentInLastYear();
    }

    public boolean isUnknown(Object arg) {
        if (!(arg instanceof Customer) || arg instanceof UnknownCustomer) {
            throw new IllegalArgumentException("잘못된 값과 비교");
        }

        return ((Customer) arg).isUnknown();
    }

    public void ttteeesssttt(List<String> people) {
        checkForMiscreants(people);
    }

    public void checkForMiscreants(List<String> people) {
        for (String p : people) {
            if ("조커".equals(p)) {
                sendAlert();
                return;
            }
            if ("사루만".equals(p)) {
                sendAlert();
                return;
            }
        }
    }


    public void sendAlert() {

    }
}
















