package org.intaehwang.chapter08.moveField;

import lombok.Getter;

@Getter
public class AccountType {
    private final String name;

    public AccountType(String name) {
        this.name = name;
    }
}
