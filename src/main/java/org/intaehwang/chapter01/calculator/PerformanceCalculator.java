package org.intaehwang.chapter01.calculator;

import lombok.Getter;
import org.intaehwang.chapter01.Performance;
import org.intaehwang.chapter01.Play;

@Getter
public abstract class PerformanceCalculator {
    protected final Performance performance;
    protected final Play play;

    protected PerformanceCalculator(Performance aPerformance, Play aPlay) {
        this.performance = aPerformance;
        this.play = aPlay;
    }

    public static PerformanceCalculator create(Performance aPerformance, Play aPlay) {
        return switch (aPlay.type()) {
            case "tragedy" -> new TragedyCalculator(aPerformance, aPlay);
            case "comedy" -> new ComedyCalculator(aPerformance, aPlay);
            default -> throw new IllegalArgumentException("알 수 없는 장르: " + aPlay.type());
        };
    }

    public abstract int amount();

    public int volumeCredits() {
        int result = 0;
        result += Math.max(this.performance.audience() - 30, 0);
        if ("comedy".equals(this.play.type())) {
            result += this.performance.audience() / 5;
        }

        return result;
    }
}
