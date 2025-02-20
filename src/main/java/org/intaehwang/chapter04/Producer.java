package org.intaehwang.chapter04;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Producer {
    private final Province province;
    private int cost;
    private final String name;
    private int production;

    @Builder
    protected Producer(Province province, int cost, String name, int production) {
        this.province = province;
        this.cost = cost;
        this.name = name;
        this.production = production;
    }

    public void setCost(String arg) {
        this.cost = Integer.parseInt(arg);
    }

    public void setProduction(String amountStr) {
        int newProduction = 0;
        try {
            newProduction = Integer.parseInt(amountStr);
        } catch (NumberFormatException ignore) {
        }

        this.province.setTotalProduction(province.getTotalProduction() + newProduction - this.production);
        this.production = newProduction;
    }
}
