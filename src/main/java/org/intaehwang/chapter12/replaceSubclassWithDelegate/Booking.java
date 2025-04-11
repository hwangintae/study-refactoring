package org.intaehwang.chapter12.replaceSubclassWithDelegate;

import lombok.Getter;

import java.time.Instant;

@Getter
public class Booking {
    private final Show show;
    private final Instant date;
    private PremiumBookingDelegate premiumDelegate;

    public Booking(Show show, Instant date) {
        this.show = show;
        this.date = date;
    }

    public static Booking createBooking(Show show, Instant date) {
        return new Booking(show, date);
    }

    public static Booking createPremiumBooking(Show show, Instant date, Extras extras) {
        Booking result = new Booking (show, date);
        result.bePremium(extras);

        return result;
    }

    public boolean hasTalkback() {
        return (this.premiumDelegate != null)
                ? this.premiumDelegate.hasTalkback()
                : this.show.hasOwnProperty("talkback") && !this.isPeakDay();
    }

    public boolean isPeakDay() {
        return true;
    }

    public double privateBasePrice() {
        double result = this.show.getPrice();

        if (this.isPeakDay()) result += Math.round(result * 0.15);
        return result;
    }

    public double basePrice() {
        double result = this.show.getPrice();

        if (this.isPeakDay()) result += Math.round(result * 0.15);
        return (this.premiumDelegate != null)
                ? this.premiumDelegate.extendBasePrice(result)
                : result;
    }

    public void bePremium(Extras extras) {
        this.premiumDelegate = new PremiumBookingDelegate(this, extras);
    }
}
