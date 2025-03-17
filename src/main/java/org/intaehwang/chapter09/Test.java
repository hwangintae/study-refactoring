package org.intaehwang.chapter09;

import org.intaehwang.chapter09.splitVariable.Scenario;

public class Test {

    public double distanceTravelled(Scenario scenario, int time) {
        double result;
        double primaryAcceleration = 1.0 * scenario.getPrimaryForce() / scenario.getMass();
        int primaryTime = Math.min(time, scenario.getDelay());

        result = 0.5 * primaryAcceleration * primaryTime * primaryTime;

        int secondaryTime = time - scenario.getDelay();
        if (secondaryTime > 0) {
            double primaryVelocity = primaryAcceleration * scenario.getDelay();

            double secondaryAcceleration = 1.0 * (scenario.getPrimaryForce() + scenario.getSecondaryForce()) / scenario.getMass();

            result += primaryVelocity * secondaryTime + 0.5 * secondaryAcceleration * secondaryTime * secondaryTime;
        }

        return result;
    }

    public int discount(int inputValue, int quantity) {
        int result = inputValue;

        if (inputValue > 50) result = result - 2;
        if (quantity > 100) result = result - 1;

        return result;
    }
}
