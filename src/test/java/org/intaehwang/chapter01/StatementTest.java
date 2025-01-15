package org.intaehwang.chapter01;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class StatementTest {

    @Test
    public void test1() {
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

        Statement statement = new Statement(invoice, plays);

        // when
        String result = statement.getResult();

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
}