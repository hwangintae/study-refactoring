package org.intaehwang.chapter01;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public record RenderPlainText(Statement.StatementData data, Map<String, Play> plays) {
    public String renderPlainText() {
        String result = "청구 내역 (고객명: " + data.getCustomer() + ")\n";
        for (Statement.EnrichPerformance perf : data.getPerformances()) {
            // 청구 내욕을 출력한다.
            result += "  " + perf.getPlay().name() + ": " + usd(perf.getAmount()) + " (" + perf.getAudience() + "석)\n";
        }

        result += "총액: " + usd(data.getTotalAmount()) + "\n";
        result += "적립 포인트: " + data.getTotalVolumeCredits() + "점\n";

        return result;
    }



    private String usd(double aNumber) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(aNumber / 100.0);
    }
}
