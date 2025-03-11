package org.intaehwang.chapter08.moveFunction;

import java.awt.*;

public class Distance {

    public Distance() {}

    public static double calculate(Point[] points) {
        double result = 0;

        for (int i = 1; i < points.length; i++) {
            result += distance(points[i - 1], points[i]);
        }

        return result;
    }

    public static double distance(Point p1, Point p2) {
        int EARTH_RADIUS = 3959;

        double dLat = radians(p2.getY()) - radians(p1.getY());
        double dLon = radians(p2.getX()) - radians(p1.getX());

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(radians(p2.getY()))
                * Math.cos(radians(p1.getY()))
                * Math.pow(Math.sin(dLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public static double radians(double degrees) {
        return degrees * Math.PI / 180;
    }
}
