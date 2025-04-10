package org.intaehwang.chapter12.replaceSubclassWithDelegate;

public class NorwegianBlueParrotDelegate extends SpeciesDelegate {
    private String plumage;
    private int voltage;
    private boolean isNailed;

    public NorwegianBlueParrotDelegate(Species data) {
        this.plumage = data.plumage();
        this.voltage = data.voltage();
        this.isNailed = data.isNailed();
    }

    @Override
    public int airSpeedVelocity() {
        return (this.isNailed) ? 0 : 10 + this.voltage / 10;
    }

    public String plumage() {
        if (this.voltage > 100) return "그을렸다";
        else return (this.plumage != null) ? this.plumage : "예쁘다";
    }
}
