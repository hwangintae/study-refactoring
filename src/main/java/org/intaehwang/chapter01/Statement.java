package org.intaehwang.chapter01;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class Statement {
    private final Invoice invoice;
    private final Map<String, Play> plays;
    private int totalAmount = 0;
    private int volumeCredits = 0;
    private String result;

    NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

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
        for (Performance perf : invoice.performances()) {
            Play play = plays.get(perf.playId());
            int thisAmount = 0;

            switch (play.type()) {
                case "tragedy": // 비극
                    thisAmount = 40_000;
                    if (perf.audience() > 30) {
                        thisAmount += 1_000 * (perf.audience() - 30);
                    }
                    break;
                case "comedy": // 희극
                    thisAmount = 30_000;
                    if (perf.audience() > 20) {
                        thisAmount += 10_000 + 500 * (perf.audience() - 20);
                    }
                    thisAmount += 300 * perf.audience();
                    break;
                default:
                    throw new IllegalArgumentException("알 수 없는 장르: " + play.type());
            }

            // 포인트를 적립한다.
            volumeCredits += Math.max(perf.audience() - 30, 0);
            // 희극 관객 5명마다 추가 포인트를 제공한다.
            if ("comedy".equals(play.type())) {
                volumeCredits += perf.audience() / 5;
            }

            // 청구 내욕을 출력한다.
            result += "  " + play.name() + ": " + format.format(thisAmount / 100.0) + " (" + perf.audience() + "석)\n";
            totalAmount += thisAmount;
        }
        result += "총액: " + format.format(totalAmount / 100.0) + "\n";
        result += "적립 포인트: " + volumeCredits + "점\n";

        return this.result;
    }
}
