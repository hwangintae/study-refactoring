package org.intaehwang.chapter12.replaceSubclassWithDelegate;

import java.time.Instant;

public class PremiumBooking extends Booking {
    private final Extras extras;

    public PremiumBooking(Show show, Instant date, Extras extras) {
        super(show, date);
        this.extras = extras;
    }

    @Override
    public double basePrice() {
        return Math.round(super.basePrice() + this.extras.premiumFee());
    }

    @Override
    public boolean isPeakDay() {
        return false;
    }

    public boolean hasDinner() {
        return this.extras.hasOwnProperty("dinner") && !this.isPeakDay();
    }
}
