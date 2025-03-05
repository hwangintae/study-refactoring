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
}
