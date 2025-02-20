package org.intaehwang.chapter04;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class Province {
    private final String name;
    private final List<Producer> producers;
    private int totalProduction;
    private int demand;
    private int price;

    @Builder
    protected Province(String name, List<Producer> producers, int totalProduction, int demand, int price) {
        this.name = name;
        this.producers = producers;
        this.totalProduction = totalProduction;
        this.demand = demand;
        this.price = price;
    }

    public static Province create(ProvinceData doc) {
        Province province = Province.builder()
                .name(doc.getName())
                .producers(new ArrayList<>())
                .totalProduction(0)
                .demand(doc.getDemand())
                .price(doc.getPrice())
                .build();

        doc.getProducers().forEach(d -> province.addProducer(Producer.builder()
                .province(province)
                .cost(d.getCost())
                .name(d.getName())
                .production(d.getProduction())
                .build()));

        return province;
    }


    public void addProducer(Producer arg) {
        this.producers.add(arg);
        this.totalProduction += arg.getProduction();
    }

    public int getShortfall() {
        return this.demand - this.totalProduction;
    }

    public int getProfit() {
        return getDemandValue() - getDemandCost();
    }

    public int getDemandValue() {
        return getSatisfiedDemand() * this.price;
    }

    public int getSatisfiedDemand() {
        return Math.min(this.demand, this.totalProduction);
    }

    public int getDemandCost() {
        int remainingDemand = this.demand;
        int result = 0;

        List<Producer> sortedProducers = this.producers.stream()
                .sorted(Comparator.comparingInt(Producer::getCost))
                .toList();

        for (Producer p : sortedProducers) {
            int contribution = Math.min(remainingDemand, p.getProduction());
            remainingDemand -= contribution;
            result += contribution * p.getCost();
        }

        return result;
    }

    @SuppressWarnings({})
    public void setTotalProduction(int arg) {
        this.totalProduction = arg;
    }

    public void setDemand(String arg) {
        this.demand = Integer.parseInt(arg);
    }

    public void setPrice(String arg) {
        this.price = Integer.parseInt(arg);
    }

}
