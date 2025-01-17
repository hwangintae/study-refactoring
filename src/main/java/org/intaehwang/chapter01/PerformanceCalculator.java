package org.intaehwang.chapter01;

import lombok.Getter;

@Getter
public class PerformanceCalculator {
    private final Performance performance;
    private final Play play;

    protected PerformanceCalculator(Performance aPerformance, Play aPlay) {
        this.performance = aPerformance;
        this.play = aPlay;
    }

    public int amount() {
        int result = 0;
        switch (this.play.type()) {
            case "tragedy": // 비극
                result = 40_000;
                if (this.performance.audience() > 30) {
                    result += 1_000 * (this.performance.audience() - 30);
                }
                break;
            case "comedy": // 희극
                result = 30_000;
                if (this.performance.audience() > 20) {
                    result += 10_000 + 500 * (this.performance.audience() - 20);
                }
                result += 300 * this.performance.audience();
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 장르: " + this.play.type());
        }

        return result;
    }
}
