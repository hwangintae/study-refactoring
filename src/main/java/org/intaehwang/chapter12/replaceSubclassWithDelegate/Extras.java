package org.intaehwang.chapter12.replaceSubclassWithDelegate;

public record Extras(double premiumFee) {

    public boolean hasOwnProperty(String str) {
        return true;
    }
}
