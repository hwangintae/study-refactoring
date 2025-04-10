package org.intaehwang.chapter12.replaceSubclassWithDelegate;

import lombok.Getter;

@Getter
public class Bird {
    private String name;
    private String plumage;
    private SpeciesDelegate speciesDelegate;

    public Bird(Species data) {
        this.name = data.name();
        this.plumage = data.plumage();
        this.speciesDelegate = this.selectSpeciesDelegate(data);
    }

    public SpeciesDelegate selectSpeciesDelegate(Species data) {
        return switch (data.name()) {
            case "유럽 제비" -> new EuropeanSwallowDelegate();
            case "아프리카 제비" -> new AfricanSwallowDelegate(data);
            case "노르웨이 파랑 앵무" -> new NorwegianBlueParrotDelegate(data);
            default -> throw new IllegalStateException("Unexpected value: " + data.name());
        };
    }

    public String getPlumage() {

        return (this.plumage != null) ? this.plumage : "보통이다";
    }

    public int airSpeedVelocity() {
        return speciesDelegate != null ? speciesDelegate.airSpeedVelocity() : 0;
    }
}
