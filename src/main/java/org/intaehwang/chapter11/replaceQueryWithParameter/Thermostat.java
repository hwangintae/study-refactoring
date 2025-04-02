package org.intaehwang.chapter11.replaceQueryWithParameter;

public class Thermostat {
    private final int temperature;

    public Thermostat(int temperature) {
        this.temperature = temperature;
    }

    public int selectedTemperature() {
        return this.temperature;
    }
}
