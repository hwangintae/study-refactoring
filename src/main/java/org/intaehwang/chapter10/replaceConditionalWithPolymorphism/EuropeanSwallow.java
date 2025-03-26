package org.intaehwang.chapter10.replaceConditionalWithPolymorphism;

public class EuropeanSwallow extends Bird {
    public EuropeanSwallow(String name, String type, int numberOfCoconuts, int voltage, boolean isNailed) {
        super(name, type, numberOfCoconuts, voltage, isNailed);
    }

    @Override
    public String plumage() {
        return "보통이다";
    }

    @Override
    public int airSpeedVelocity() {
        return 35;
    }
}