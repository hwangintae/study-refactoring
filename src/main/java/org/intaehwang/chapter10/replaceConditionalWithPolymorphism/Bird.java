package org.intaehwang.chapter10.replaceConditionalWithPolymorphism;

import lombok.Getter;

@Getter
public class Bird {
    protected final String name;
    protected final String type;
    protected final int numberOfCoconuts;
    protected final int voltage;
    protected final boolean isNailed;

    protected Bird(String name, String type, int numberOfCoconuts, int voltage, boolean isNailed) {
        this.name = name;
        this.type = type;
        this.numberOfCoconuts = numberOfCoconuts;
        this.voltage = voltage;
        this.isNailed = isNailed;
    }

    public static Bird create(String name, String type, int numberOfCoconuts, int voltage, boolean isNailed) {
        return switch (type) {
            case "유럽 제비" -> new EuropeanSwallow(name, type, numberOfCoconuts, voltage, isNailed);
            case "아프리카 제비" -> new AfricanSwallow(name, type, numberOfCoconuts, voltage, isNailed);
            case "노르웨이 파랑 앵무" -> new NorwegianBlueParrot(name, type, numberOfCoconuts, voltage, isNailed);
            default -> new Bird(name, type, numberOfCoconuts, voltage, isNailed);
        };
    }

    public String plumage() {
        return "알 수 없다";
    }

    public int airSpeedVelocity() {
        return 0;
    }
}
