package org.intaehwang.chapter12.replaceSubclassWithDelegate;

public class AfricanSwallowDelegate extends SpeciesDelegate {
    private int numberOfCoCount;

    public AfricanSwallowDelegate(Species data) {
        this.numberOfCoCount = data.numberOfCoCount();
    }

    @Override
    public int airSpeedVelocity() {
        return 40 - 2 * this.numberOfCoCount;
    }
}
