package org.intaehwang.chapter11;

import org.intaehwang.chapter11.preserveWholeObject.DayTempRange;
import org.intaehwang.chapter11.preserveWholeObject.Room;
import org.intaehwang.chapter11.removeFlagArgument.Order;
import org.intaehwang.chapter11.removeFlagArgument.PlaceOn;
import org.intaehwang.chapter11.replaceFunctionWithCommand.Candidate;
import org.intaehwang.chapter11.replaceFunctionWithCommand.MedicalExam;
import org.intaehwang.chapter11.replaceFunctionWithCommand.ScoringGuide;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Chapter11TestTest {

    @Test
    public void deliveryDateTestMATure() {
        // given
        Order order = new Order("MA", new PlaceOn(2));

        // when
        int result = Chapter11Test.deliveryDate(order, true);

        // then
        assertThat(result).isEqualTo(4);
    }

    @Test
    public void deliveryDateTestNHTure() {
        // given
        Order order = new Order("NH", new PlaceOn(2));

        // when
        int result = Chapter11Test.deliveryDate(order, true);

        // then
        assertThat(result).isEqualTo(5);
    }

    @Test
    public void deliveryDateTestKOTure() {
        // given
        Order order = new Order("KO", new PlaceOn(2));

        // when
        int result = Chapter11Test.deliveryDate(order, true);

        // then
        assertThat(result).isEqualTo(6);
    }

    @Test
    public void deliveryDateTestMAFalse() {
        // given
        Order order = new Order("MA", new PlaceOn(2));

        // when
        int result = Chapter11Test.deliveryDate(order, false);

        // then
        assertThat(result).isEqualTo(6);
    }

    @Test
    public void deliveryDateTestNHFalse() {
        // given
        Order order = new Order("NH", new PlaceOn(2));

        // when
        int result = Chapter11Test.deliveryDate(order, false);

        // then
        assertThat(result).isEqualTo(7);
    }

    @Test
    public void deliveryDateTestKOFalse() {
        // given
        Order order = new Order("KO", new PlaceOn(2));

        // when
        int result = Chapter11Test.deliveryDate(order, false);

        // then
        assertThat(result).isEqualTo(8);
    }

    @Test
    public void checkWithinRangeTest() {
        // given
        Room room = new Room(new DayTempRange(9, 21));

        // when
        String result = Chapter11Test.checkWithinRange(room);

        // then
        assertThat(result).isEqualTo("방 온도가 지정 범위를 벗어났습니다.");
    }

    @Test
    public void checkWithinRangeTest2() {
        // given
        Room room = new Room(new DayTempRange(9, 21));

        // when
        String result = Chapter11Test.checkWithinRange2(room);

        // then
        assertThat(result).isEqualTo("방 온도가 지정 범위를 벗어났습니다.");
    }

    @Test
    public void scoreTest1() {
        // given
        Candidate candidate = new Candidate(false);
        MedicalExam medicalExam = new MedicalExam(false);
        ScoringGuide scoringGuide = new ScoringGuide();

        // when
        int result = Chapter11Test.score(candidate, medicalExam, scoringGuide);

        // then
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void scoreTest2() {
        // given
        Candidate candidate = new Candidate(true);
        MedicalExam medicalExam = new MedicalExam(false);
        ScoringGuide scoringGuide = new ScoringGuide();

        // when
        int result = Chapter11Test.score(candidate, medicalExam, scoringGuide);

        // then
        assertThat(result).isEqualTo(-5);
    }

    @Test
    public void scoreTest3() {
        // given
        Candidate candidate = new Candidate(false);
        MedicalExam medicalExam = new MedicalExam(true);
        ScoringGuide scoringGuide = new ScoringGuide();

        // when
        int result = Chapter11Test.score(candidate, medicalExam, scoringGuide);

        // then
        assertThat(result).isEqualTo(-5);
    }

    @Test
    public void scoreTest4() {
        // given
        Candidate candidate = new Candidate(true);
        MedicalExam medicalExam = new MedicalExam(true);
        ScoringGuide scoringGuide = new ScoringGuide();

        // when
        int result = Chapter11Test.score(candidate, medicalExam, scoringGuide);

        // then
        assertThat(result).isEqualTo(-10);
    }
}