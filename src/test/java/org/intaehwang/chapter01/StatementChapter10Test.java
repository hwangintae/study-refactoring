package org.intaehwang.chapter01;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class StatementChapter10Test {

    @Test
    public void statementTest() {
        // given
        List<Performance> performances = List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)
        );

        Invoice invoice = new Invoice("BigCo", performances);

        Map<String, Play> plays = new HashMap<>();

        plays.put("hamlet", new Play("Hamlet", "tragedy"));
        plays.put("as-like", new Play("As You Like It", "comedy"));
        plays.put("othello", new Play("Othello", "tragedy"));

        Statement statement = new Statement();

        // when
        String result = statement.statement(invoice, plays);

        // then
        String answer = """
                청구 내역 (고객명: BigCo)
                  Hamlet: $650.00 (55석)
                  As You Like It: $580.00 (35석)
                  Othello: $500.00 (40석)
                총액: $1,730.00
                적립 포인트: 47점
                """;

        assertThat(result).isEqualTo(answer);
    }

    @Test
    public void htmlStatementTest() {
        // given
        List<Performance> performances = List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)
        );

        Invoice invoice = new Invoice("BigCo", performances);

        Map<String, Play> plays = new HashMap<>();

        plays.put("hamlet", new Play("Hamlet", "tragedy"));
        plays.put("as-like", new Play("As You Like It", "comedy"));
        plays.put("othello", new Play("Othello", "tragedy"));

        Statement statement = new Statement();

        // when
        String result = statement.htmlStatement(invoice, plays);

        // then
        String answer = """
                <h1>청구 내역 (고객명: BigCo)</h1>
                <table>
                <tr><th>연극</th><th>좌석 수</th><th>금액</th></tr>
                  <tr><td>Hamlet</td><td>(55)</td><td>$650.00</td></tr>
                  <tr><td>As You Like It</td><td>(35)</td><td>$580.00</td></tr>
                  <tr><td>Othello</td><td>(40)</td><td>$500.00</td></tr>
                </table>
                <p>총액: <em>$1,730.00</em></p>
                <p>적립 포인트: <em>47</em>점</p>
                """;

        assertThat(result).isEqualTo(answer);
    }
}