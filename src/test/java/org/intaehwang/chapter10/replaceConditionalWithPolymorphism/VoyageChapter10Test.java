package org.intaehwang.chapter10.replaceConditionalWithPolymorphism;

import org.intaehwang.chapter10.Chapter10Test;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VoyageChapter10Test {

    @Test
    public void voyageRatingTest() {
        // given
        Chapter10Test chapter10Test = new Chapter10Test();
        Voyage voyage = new Voyage("서인도", 10);
        List<History> histories = List.of(
                new History("동인도", 5),
                new History("서인도", 15),
                new History("중국", -2),
                new History("서아프리카", 7)
        );

        // when
        String rating = chapter10Test.rating(voyage, histories);

        // then
        assertThat(rating).isEqualTo("B");
    }

    @Test
    public void voyageChinaRatingTest() {
        // given
        Chapter10Test chapter10Test = new Chapter10Test();
        Voyage voyage = new Voyage("중국", 10);
        List<History> histories = List.of(
                new History("동인도", 5),
                new History("서인도", 15),
                new History("중국", -2),
                new History("서아프리카", 7)
        );

        // when
        String rating = chapter10Test.rating(voyage, histories);

        // then
        assertThat(rating).isEqualTo("A");
    }

}