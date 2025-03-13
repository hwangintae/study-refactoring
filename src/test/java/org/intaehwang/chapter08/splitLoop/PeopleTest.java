package org.intaehwang.chapter08.splitLoop;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

}