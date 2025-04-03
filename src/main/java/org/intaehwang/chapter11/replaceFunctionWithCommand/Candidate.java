package org.intaehwang.chapter11.replaceFunctionWithCommand;

import lombok.Getter;

@Getter
public class Candidate {
    private final boolean originState;

    public Candidate(boolean originState) {
        this.originState = originState;
    }
}
