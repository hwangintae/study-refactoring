package org.intaehwang.chapter01.calculator;

import org.intaehwang.chapter01.Performance;
import org.intaehwang.chapter01.Play;

public class TragedyCalculator extends PerformanceCalculator {
    protected TragedyCalculator(Performance aPerformance, Play aPlay) {
        super(aPerformance, aPlay);
    }

    @Override
    public int amount() {
        int result = 40_000;
        if (this.performance.audience() > 30) {
            result += 1_000 * (this.performance.audience() - 30);
        }

        return result;
    }
}
