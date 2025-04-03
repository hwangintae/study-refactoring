package org.intaehwang.chapter11.replaceCommandWithFunction;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ChargeCalculator {

    public static int charge(Customer customer, int usage, Provider provider) {
        int baseCharge = customer.getBaseRate() * usage;
        return baseCharge + provider.getConnectionCharge();
    }
}
