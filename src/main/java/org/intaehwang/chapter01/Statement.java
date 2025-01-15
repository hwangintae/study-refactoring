package org.intaehwang.chapter01;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class Statement {
    private final Invoice invoice;
    private final Map<String, Play> plays;
    private String result;

    public Statement(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
        this.result = "청구 내역 (고객명: " + invoice.customer() + ")\n";
    }

    public Invoice getInvoice() {
        return this.invoice;
    }

    public Map<String, Play> getPlays() {
        return this.plays;
    }

    public String getResult() {
        int totalAmount = 0;
        for (Performance perf : invoice.performances()) {
            // 청구 내욕을 출력한다.
            result += "  " + playFor(perf).name() + ": " + usd(amountFor(perf)) + " (" + perf.audience() + "석)\n";
            totalAmount += amountFor(perf);
        }

        int volumeCredits = 0;
        for (Performance perf : invoice.performances()) {
            // 포인트를 적립한다.
            volumeCredits += volumeCreditsFor(perf);
        }

        result += "총액: " + usd(totalAmount) + "\n";
        result += "적립 포인트: " + volumeCredits + "점\n";

        return this.result;
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

    private Play playFor(Performance aPerformance) {
        return plays.get(aPerformance.playId());
    }

    private int volumeCreditsFor(Performance aPerformance) {
        int result = 0;
        result += Math.max(aPerformance.audience() - 30, 0);
        if ("comedy".equals(playFor(aPerformance).type())) {
            result += aPerformance.audience() / 5;
        }

        return result;
    }

    private String usd(double aNumber) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(aNumber / 100.0);
    }
}
