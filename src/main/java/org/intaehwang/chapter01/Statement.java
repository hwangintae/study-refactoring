package org.intaehwang.chapter01;

import java.util.List;
import java.util.Map;

public record Statement(Invoice invoice, Map<String, Play> plays) {

    public String statement(Invoice invoice, Map<String, Play> plays) {
        String customer = invoice.customer();
        List<Performance> performances = invoice.performances().stream()
                .map(this::enrichPerformance)
                .toList();

        StatementData statementData = new StatementData(customer, performances);

        return new RenderPlainText(statementData, invoice, plays).renderPlainText();
    }

    private Performance enrichPerformance(Performance aPerformance) {
        return aPerformance;
    }

    public record StatementData(String customer, List<Performance> performances) {
    }
}
