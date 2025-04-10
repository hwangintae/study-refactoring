package org.intaehwang.chapter12.replaceSubclassWithDelegate;

public class PremiumBookingDelegate {
    private final Booking host;
    private final Extras extras;

    public PremiumBookingDelegate(Booking host, Extras extras) {
        this.host = host;
        this.extras = extras;
    }

    public boolean hasTalkback() {
        return this.host.getShow().hasOwnProperty("talkback");
    }

    public boolean isPeakDay() {
        return true;
    }

    public double basePrice() {
        return Math.round(this.host.privateBasePrice() + this.extras.premiumFee());
    }

    public double extendBasePrice(double base) {
        return Math.round(base + this.extras.premiumFee());
    }
}
