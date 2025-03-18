package org.intaehwang.chapter09.replaceDerivedVariableWithQuery;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductionPlanTest {


    @Test
    public void test() {
        // given
        int production = 10;
        List<Adjustment> adjustments = List.of(new Adjustment(10));

        ProductionPlan productionPlan = new ProductionPlan(production, adjustments);

        // when
        int result = productionPlan.getProduction();

        // then
        assertThat(production).isEqualTo(result);
    }

    @Test
    public void emptyList() {
        // given
        int production = 0;
        List<Adjustment> adjustments = List.of();

        ProductionPlan productionPlan = new ProductionPlan(production, adjustments);

        // when
        int result = productionPlan.getProduction();

        // then
        assertThat(production).isEqualTo(result);
    }

    @Test
    public void productionIsNotZero() {
        // given
        ProductionPlan productionPlan = new ProductionPlan();

        // when
        int result = productionPlan.getProduction();

        // then
        assertThat(result).isEqualTo(0);
    }
}