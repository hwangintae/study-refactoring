package org.intaehwang.chapter08.splitLoop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

class PeopleTest {

    @Test
    public void test() {
        // given
        List<People> peoples = List.of(
                new People(1, 1),
                new People(6, 6),
                new People(3, 3),
                new People(7, 7),
                new People(4, 4),
                new People(8, 8),
                new People(5, 5),
                new People(2, 2)
        );

        int infinity = Integer.MAX_VALUE;

        // when
        int totalSalary = getTotalSalary(peoples);

        int youngest = getYoungest(peoples);

        // then
        assertThat(youngest).isEqualTo(1);
        assertThat(totalSalary).isEqualTo(36);
    }

    int getTotalSalary(List<People> peoples) {
        return peoples.stream()
                .map(People::getSalary)
                .reduce(0, Integer::sum);
    }

    int getYoungest(List<People> peoples) {
        return peoples.stream()
                .map(People::getAge)
                .min(Integer::compareTo)
                .orElseThrow(() -> new IllegalStateException("empty people"));
    }

    @Test
    public void replaceLoopWithPipeline() {
        // given
        String input = """
                office, country, telephone
                Chicago, USA, +1 312 373 1000
                Beijing, China, +86 4008 900 505
                Bangalore, India, +91 80 4064 9570
                Porto Alegre, Brazil, +55 51 3079 3550
                Chennai, India, +91 44 660 44766
                """;

        // when
        List<Map<String, String>> result = acquireData(input);

        // then
        assertThat(result)
                .containsExactly(
                        Map.of("city", "Bangalore", "phone", "+91 80 4064 9570"),
                        Map.of("city", "Chennai", "phone", "+91 44 660 44766")
                );

    }

    List<Map<String, String>> acquireData(String input) {
        String[] lines = input.split("\n");

        return Arrays.stream(lines)
                .skip(1)
                .filter(line -> !line.isBlank())
                .map(line -> line.split(","))
                .filter(record -> record[1].trim().equals("India"))
                .map(record -> Map.of("city", record[0].trim(),
                        "phone", record[2].trim()))
                .toList();
    }

}