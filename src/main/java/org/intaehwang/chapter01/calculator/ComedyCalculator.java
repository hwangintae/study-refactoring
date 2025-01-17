package org.intaehwang.chapter01.calculator;

import org.intaehwang.chapter01.Performance;
import org.intaehwang.chapter01.Play;

public class ComedyCalculator extends PerformanceCalculator {
    protected ComedyCalculator(Performance aPerformance, Play aPlay) {
        super(aPerformance, aPlay);
    }

    @Override
    public int amount() {
        int result = 30_000;
        if (this.performance.audience() > 20) {
            result += 10_000 + 500 * (this.performance.audience() - 20);
        }
        result += 300 * this.performance.audience();

        return result;
    }
}
