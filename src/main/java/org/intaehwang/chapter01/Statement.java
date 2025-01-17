package org.intaehwang.chapter01;

import org.intaehwang.chapter01.StatementData.EnrichPerformance;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public record Statement(Invoice invoice, Map<String, Play> plays) {

    public String statement() {
        return renderPlainText(StatementData.create(invoice, plays));
    }

    private String renderPlainText(StatementData data) {
        String result = "청구 내역 (고객명: " + data.getCustomer() + ")\n";
        for (EnrichPerformance perf : data.getPerformances()) {
            // 청구 내욕을 출력한다.
            result += "  " + perf.getPlay().name() + ": " + usd(perf.getAmount()) + " (" + perf.getAudience() + "석)\n";
        }

        result += "총액: " + usd(data.getTotalAmount()) + "\n";
        result += "적립 포인트: " + data.getTotalVolumeCredits() + "점\n";

        return result;
    }

    public String htmlStatement(Invoice invoice, Map<String, Play> plays) {
        return renderHtml(new StatementData(invoice, plays));
    }

    private String renderHtml(StatementData data) {
        List<String> result = List.of(
                "<h1>청구 내역 (고객명: " + data.getCustomer() + ")</h1>\n",
                "<table>\n",
                "<tr><th>연극</th><th>좌석 수</th><th>금액</th></tr>",
                data.getPerformances().stream()
                        .map(perf -> "  <tr><td>" + perf.getPlay().name() + "</td><td>(" + perf.getAudience() + ")</td>" +
                                "<td>" + usd(perf.getAmount()) + "</td></tr>\n"
                        ).reduce("", (a, b) -> a + b),
                "</table>\n>",
                "<p>총액: <em>" + usd(data.getTotalAmount()) + "</em></p>\n",
                "<p>적립 포인트: <em>" + data.getTotalVolumeCredits() + "</em>점</p>\n"
        );

        return String.join("", result);
    }

    private String usd(double aNumber) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(aNumber / 100.0);
    }

}
