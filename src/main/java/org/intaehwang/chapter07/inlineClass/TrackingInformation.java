package org.intaehwang.chapter07.inlineClass;

public class TrackingInformation {
    private String shippingCompany;
    private String trackingNumber;

    public TrackingInformation(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }

    public String getShippingCompany() {
        return shippingCompany;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setShippingCompany(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String display() {
        return shippingCompany + ":" + trackingNumber;
    }
}
