package org.intaehwang.chapter07.inlineClass;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentTest {

    private Shipment shipment;

    @BeforeEach
    void setUp() {
        shipment = new Shipment("우직이", "123456789");
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("우직이", shipment.getShippingCompany());
        assertEquals("123456789", shipment.getTrackingNumber());
    }

    @Test
    void testTrackingInfoDisplay() {
        assertEquals("우직이:123456789", shipment.getTrackingInfo());
    }

    @Test
    void testSetTrackingInformation() {
        shipment.setShippingCompany("행복이");
        shipment.setTrackingNumber("987654321");

        assertEquals("행복이", shipment.getShippingCompany());
        assertEquals("987654321", shipment.getTrackingNumber());
        assertEquals("행복이:987654321", shipment.getTrackingInfo());
    }

}