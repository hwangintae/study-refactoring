package org.intaehwang.chapter07.inlineClass;

public class Shipment {
    private TrackingInformation trackingInformation;

    public Shipment(TrackingInformation trackingInformation) {
        this.trackingInformation = trackingInformation;
    }

    public TrackingInformation getTrackingInformation() {
        return trackingInformation;
    }

    public void setTrackingInformation(TrackingInformation trackingInformation) {
        this.trackingInformation = trackingInformation;
    }

    public String getTrackingInfo() {
        return this.trackingInformation.display();
    }

    public String getShippingCompany() {
        return this.trackingInformation.getShippingCompany();
    }

    public String getTrackingNumber() {
        return this.trackingInformation.getTrackingNumber();
    }

    public void setShippingCompany(String shippingCompany) {
        this.trackingInformation.setShippingCompany(shippingCompany);
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingInformation.setTrackingNumber(trackingNumber);
    }
}
