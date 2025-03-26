package org.intaehwang.chapter10.replaceConditionalWithPolymorphism;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BirdChapter10Test {

    @Test
    public void bird() {
        // given
        Bird bird = Bird.create("", "한국 제비", 0, 0, false);

        // when
        String plumage = bird.plumage();
        int airSpeedVelocity = bird.airSpeedVelocity();

        // then
        assertThat(plumage).isEqualTo("알 수 없다");
        assertThat(airSpeedVelocity).isEqualTo(0);
    }

    @Test
    public void europeanSwallow() {
        // given
        Bird bird = Bird.create("", "유럽 제비", 0, 0, false);

        // when
        String plumage = bird.plumage();
        int airSpeedVelocity = bird.airSpeedVelocity();

        // then
        assertThat(plumage).isEqualTo("보통이다");
        assertThat(airSpeedVelocity).isEqualTo(35);
        assertThat(bird).isInstanceOf(EuropeanSwallow.class);
    }

    @Test
    public void africanSwallow() {
        // given
        Bird bird = Bird.create("", "아프리카 제비", 2, 0, false);

        // when
        String plumage = bird.plumage();
        int airSpeedVelocity = bird.airSpeedVelocity();

        // then
        assertThat(plumage).isEqualTo("보통이다");
        assertThat(airSpeedVelocity).isEqualTo(36);
        assertThat(bird).isInstanceOf(AfricanSwallow.class);
    }

    @Test
    public void africanSwallowIsOver2() {
        // given
        Bird bird = Bird.create("", "아프리카 제비", 3, 0, false);

        // when
        String plumage = bird.plumage();
        int airSpeedVelocity = bird.airSpeedVelocity();

        // then
        assertThat(plumage).isEqualTo("지쳤다");
    }

    @Test
    public void norwegianBlueParrot() {
        // given
        Bird bird = Bird.create("", "노르웨이 파랑 앵무", 0, 100, false);

        // when
        String plumage = bird.plumage();
        int airSpeedVelocity = bird.airSpeedVelocity();

        // then
        assertThat(plumage).isEqualTo("예쁘다");
        assertThat(airSpeedVelocity).isEqualTo(20);
    }

    @Test
    public void norwegianBlueParrotIsOver100() {
        // given
        Bird bird = Bird.create("", "노르웨이 파랑 앵무", 0, 110, false);

        // when
        String plumage = bird.plumage();
        int airSpeedVelocity = bird.airSpeedVelocity();

        // then
        assertThat(plumage).isEqualTo("그을렸다");
        assertThat(airSpeedVelocity).isEqualTo(21);
    }

    @Test
    public void norwegianBlueParrotNaliedIsTure() {
        // given
        Bird bird = Bird.create("", "노르웨이 파랑 앵무", 0, 110, true);

        // when
        String plumage = bird.plumage();
        int airSpeedVelocity = bird.airSpeedVelocity();

        // then
        assertThat(airSpeedVelocity).isEqualTo(0);
    }

}