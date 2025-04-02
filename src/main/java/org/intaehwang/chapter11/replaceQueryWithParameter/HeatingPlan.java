package org.intaehwang.chapter11.replaceQueryWithParameter;

public class HeatingPlan {
    private final int max;
    private final int min;
    private final Thermostat thermostat;

    public HeatingPlan(int max, int min, Thermostat thermostat) {
        this.max = max;
        this.min = min;
        this.thermostat = thermostat;
    }

    public int targetTemperature() {
        return targetTemperature(thermostat.selectedTemperature());
    }

    private int targetTemperature(int selectedTemperature) {
        if (selectedTemperature > max) return this.max;
        else if (selectedTemperature < min) return this.min;
        else return selectedTemperature;
    }
}
