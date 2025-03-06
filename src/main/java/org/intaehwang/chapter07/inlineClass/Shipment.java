package org.intaehwang.chapter07.inlineClass;

public class Shipment {
    private String shippingCompany;
    private String trackingNumber;

    public Shipment(String shippingCompany, String trackingNumber) {
        this.shippingCompany = shippingCompany;
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingInfo() {
        return shippingCompany + ":" + trackingNumber;
    }

    public String getShippingCompany() {
        return this.shippingCompany;
    }

    public String getTrackingNumber() {
        return this.trackingNumber;
    }

    public void setShippingCompany(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}
