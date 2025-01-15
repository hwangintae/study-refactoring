package org.intaehwang.chapter01;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public record RenderPlainText(Statement.StatementData data, Map<String, Play> plays) {
    public String renderPlainText() {
        String result = "청구 내역 (고객명: " + data.customer() + ")\n";
        for (Statement.EnrichPerformance perf : data.performances()) {
            // 청구 내욕을 출력한다.
            result += "  " + perf.getPlay().name() + ": " + usd(perf.getAmount()) + " (" + perf.getAudience() + "석)\n";
        }

        result += "총액: " + usd(totalAmount()) + "\n";
        result += "적립 포인트: " + totalVolumeCredits() + "점\n";

        return result;
    }



    private String usd(double aNumber) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(aNumber / 100.0);
    }

    private int totalVolumeCredits() {
        int result = 0;
        for (Statement.EnrichPerformance perf : data.performances()) {
            // 포인트를 적립한다.
            result += perf.getVolumeCredits();
        }

        return result;
    }

    private int totalAmount() {
        int result = 0;
        for (Statement.EnrichPerformance perf : data.performances()) {
            // 청구 내욕을 출력한다.
            result += perf.getAmount();
        }

        return result;
    }
}
