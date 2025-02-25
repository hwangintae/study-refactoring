package org.intaehwang.chapter06.introduceParameterObject;

public record OperatingPlan(int temperatureFloor, int temperatureCeiling) {

    public record NumberRange(int min, int max) {}
    public NumberRange getNumberRange() {
        return new NumberRange(temperatureFloor, temperatureCeiling);
    }
}
