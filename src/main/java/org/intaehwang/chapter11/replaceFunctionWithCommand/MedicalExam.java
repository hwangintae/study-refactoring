package org.intaehwang.chapter11.replaceFunctionWithCommand;

import lombok.Getter;

@Getter
public class MedicalExam {
    private final boolean smoker;

    public MedicalExam(boolean smoker) {
        this.smoker = smoker;
    }
}
