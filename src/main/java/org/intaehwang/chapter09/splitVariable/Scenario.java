package org.intaehwang.chapter09.splitVariable;

import lombok.Getter;

@Getter
public class Scenario {
    private final int primaryForce;
    private final int mass;
    private final int delay;
    private final int secondaryForce;

    public Scenario(int primaryForce, int mass, int delay, int secondaryForce) {
        this.primaryForce = primaryForce;
        this.mass = mass;
        this.delay = delay;
        this.secondaryForce = secondaryForce;
    }
}
