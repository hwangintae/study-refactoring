package org.intaehwang.chapter01;

import lombok.Getter;
import org.intaehwang.chapter01.calculator.PerformanceCalculator;

import java.util.List;
import java.util.Map;

@Getter
public class StatementData {
    private final String customer;
    private final Map<String, Play> plays;
    private final List<EnrichPerformance> performances;
    private final int totalAmount;
    private final int totalVolumeCredits;

    protected StatementData (Invoice invoice, Map<String, Play> plays) {
        this.customer = invoice.customer();
        this.plays = plays;
        this.performances = invoice.performances().stream()
                .map(EnrichPerformance::new)
                .toList();
        this.totalAmount = totalAmount(performances);
        this.totalVolumeCredits = totalVolumeCredits(performances);
    }

    public static StatementData create(Invoice invoice, Map<String, Play> plays) {
        return new StatementData(invoice, plays);
    }

    @Getter
    public class EnrichPerformance {
        private final String playId;
        private final int audience;
        private final Play play;
        private final int amount;
        private final int volumeCredits;

        public EnrichPerformance(Performance aPerformance) {
            PerformanceCalculator calculator = PerformanceCalculator.create(aPerformance, playFor(aPerformance));
            this.playId = aPerformance.playId();
            this.audience = aPerformance.audience();
            this.play = calculator.getPlay();
            this.amount = calculator.amount();
            this.volumeCredits = calculator.volumeCredits();
        }
    }

    private Play playFor(Performance aPerformance) {
        return this.plays.get(aPerformance.playId());
    }

    private int amountFor(Performance aPerformance) {
        return PerformanceCalculator.create(aPerformance, playFor(aPerformance)).amount();
    }

    private int volumeCreditsFor(Performance aPerformance) {
        return PerformanceCalculator.create(aPerformance, playFor(aPerformance)).volumeCredits();
    }

    private int totalVolumeCredits(List<EnrichPerformance> data) {
        return data.stream()
                .map(EnrichPerformance::getVolumeCredits)
                .reduce(0, Integer::sum);
    }

    private int totalAmount(List<EnrichPerformance> data) {
        return data.stream()
                .map(EnrichPerformance::getAmount)
                .reduce(0, Integer::sum);
    }


}
