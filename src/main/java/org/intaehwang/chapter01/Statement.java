package org.intaehwang.chapter01;

import lombok.Getter;

import java.util.List;
import java.util.Map;

public record Statement(Invoice invoice, Map<String, Play> plays) {

    public String statement() {
        String customer = invoice.customer();
        List<EnrichPerformance> performances = invoice.performances().stream()
                .map(EnrichPerformance::new)
                .toList();

        StatementData statementData = new StatementData(customer, performances);

        return new RenderPlainText(statementData, plays).renderPlainText();
    }

    @Getter
    public class EnrichPerformance {
        private final String playId;
        private final int audience;
        private final Play play;
        private final int amount;
        private final int volumeCredits;

        public EnrichPerformance(Performance aPerformance) {
            this.playId = aPerformance.playId();
            this.audience = aPerformance.audience();
            this.play = playFor(aPerformance);
            this.amount = amountFor(aPerformance);
            this.volumeCredits = volumeCreditsFor(aPerformance);
        }
    }

    private Play playFor(Performance aPerformance) {
        return plays.get(aPerformance.playId());
    }

    private int amountFor(Performance aPerformance) {
        int result = 0;
        switch (playFor(aPerformance).type()) {
            case "tragedy": // 비극
                result = 40_000;
                if (aPerformance.audience() > 30) {
                    result += 1_000 * (aPerformance.audience() - 30);
                }
                break;
            case "comedy": // 희극
                result = 30_000;
                if (aPerformance.audience() > 20) {
                    result += 10_000 + 500 * (aPerformance.audience() - 20);
                }
                result += 300 * aPerformance.audience();
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 장르: " + playFor(aPerformance).type());
        }

        return result;
    }

    private int volumeCreditsFor(Performance aPerformance) {
        int result = 0;
        result += Math.max(aPerformance.audience() - 30, 0);
        if ("comedy".equals(playFor(aPerformance).type())) {
            result += aPerformance.audience() / 5;
        }

        return result;
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


    @Getter
    public class StatementData {
        private final String customer;
        private final List<EnrichPerformance> performances;
        private final int totalAmount;
        private final int totalVolumeCredits;

        public StatementData(String customer, List<EnrichPerformance> performances) {
            this.customer = customer;
            this.performances = performances;
            this.totalAmount = totalAmount(performances);
            this.totalVolumeCredits = totalVolumeCredits(performances);
        }
    }
}
