package org.intaehwang.chapter04;

import lombok.Getter;

import java.util.List;

@Getter
public class ProvinceData {
    private final String name;
    private final List<Producer> producers;
    private final int demand;
    private final int price;

    protected ProvinceData(String name, List<Producer> producers, int demand, int price) {
        this.name = name;
        this.producers = producers;
        this.demand = demand;
        this.price = price;
    }

    public static ProvinceData getSample() {
//    {
//        name: "Asia",
//        producers: [
//            {name: "Byzantium", cost: 10, production: 9},
//            {name: "Attalia", cost: 12, production: 10},
//            {name: "Sinope", cost: 10, production: 6}
//        ],
//        demand: 30,
//        price: 20
//    }
//    """;

        List<Producer> producers = List.of(
                Producer.builder().name("Byzantium").cost(10).production(9).build(),
                Producer.builder().name("Attalia").cost(12).production(10).build(),
                Producer.builder().name("Sinope").cost(10).production(6).build()
        );

        return new ProvinceData("Asia", producers, 30, 20);
    }
}
