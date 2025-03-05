package org.intaehwang.chapter07.inlineClass;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentTest {

    private Shipment shipment;
    private TrackingInformation trackingInformation;

    @BeforeEach
    void setUp() {
        trackingInformation = new TrackingInformation("우직이");
        trackingInformation.setTrackingNumber("123456789");
        shipment = new Shipment(trackingInformation);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("우직이", shipment.getTrackingInformation().getShippingCompany());
        assertEquals("123456789", shipment.getTrackingInformation().getTrackingNumber());
    }

    @Test
    void testTrackingInfoDisplay() {
        assertEquals("우직이:123456789", shipment.getTrackingInfo());
    }

    @Test
    void testSetTrackingInformation() {
        TrackingInformation newTracking = new TrackingInformation("행복이");
        newTracking.setTrackingNumber("987654321");
        shipment.setTrackingInformation(newTracking);

        assertEquals("행복이", shipment.getTrackingInformation().getShippingCompany());
        assertEquals("987654321", shipment.getTrackingInformation().getTrackingNumber());
        assertEquals("행복이:987654321", shipment.getTrackingInfo());
    }

    @Test
    void testTrackingInformationSetters() {
        trackingInformation.setShippingCompany("행복이");
        trackingInformation.setTrackingNumber("111222333");

        assertEquals("행복이", trackingInformation.getShippingCompany());
        assertEquals("111222333", trackingInformation.getTrackingNumber());
        assertEquals("행복이:111222333", trackingInformation.display());
    }

}