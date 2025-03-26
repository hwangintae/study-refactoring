package org.intaehwang.chapter10.replaceConditionalWithPolymorphism;

public class AfricanSwallow extends Bird {
    protected AfricanSwallow(String name, String type, int numberOfCoconuts, int voltage, boolean isNailed) {
        super(name, type, numberOfCoconuts, voltage, isNailed);
    }

    @Override
    public String plumage() {
        return (this.numberOfCoconuts > 2) ? "지쳤다" : "보통이다";
    }

    @Override
    public int airSpeedVelocity() {
        return 40 - 2 * this.numberOfCoconuts;
    }
}
