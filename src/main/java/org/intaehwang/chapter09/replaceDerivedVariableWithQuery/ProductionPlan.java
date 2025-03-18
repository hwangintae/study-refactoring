package org.intaehwang.chapter09.replaceDerivedVariableWithQuery;

import java.util.ArrayList;
import java.util.List;

public class ProductionPlan {
    private int production;
    private List<Adjustment> adjustments = new ArrayList<>();

    public ProductionPlan() {}

    public ProductionPlan(int production, List<Adjustment> adjustments) {
        this.production = production;
        this.adjustments = adjustments;
    }

    public int getProduction() {
        assert this.production == calculatedProduction();
        return this.production;
    }

    public void applyAdjustment(Adjustment anAdjustment) {
        this.adjustments.add(anAdjustment);
        this.production += anAdjustment.getAmount();
    }

    public int calculatedProduction() {
        return adjustments.stream()
                .map(Adjustment::getAmount)
                .reduce(0, Integer::sum);
    }
}
