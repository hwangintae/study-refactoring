package org.intaehwang.chapter06.splitPhase;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public class CommandLine {
    private final String[] args;

    public CommandLine(String[] args) {
        this.args = args;
    }

    public String filename() {
        return this.args[this.args.length - 1];
    }

    public boolean onlyCountReady() {
        return Stream.of(this.args).anyMatch(arg -> "-r".equals(arg));
    }
}
