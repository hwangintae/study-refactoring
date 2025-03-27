package org.intaehwang.chapter10.replaceConditionalWithPolymorphism;

public class NorwegianBlueParrot extends Bird {
    protected NorwegianBlueParrot(String name, String type, int numberOfCoconuts, int voltage, boolean isNailed) {
        super(name, type, numberOfCoconuts, voltage, isNailed);
    }

    @Override
    public String plumage() {
        return (this.voltage > 100) ? "그을렸다" : "예쁘다";
    }

    @Override
    public int airSpeedVelocity() {
        return (this.isNailed) ? 0 : 10 + this.voltage / 10;
    }
}
