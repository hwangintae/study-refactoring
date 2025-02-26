package org.intaehwang.chapter06.splitPhase;

import lombok.Getter;

@Getter
public class CommandLine {
    private final String[] args;

    public CommandLine(String[] args) {
        this.args = args;
    }

    public String filename(String[] args) {
        return args[args.length - 1];
    }
}
