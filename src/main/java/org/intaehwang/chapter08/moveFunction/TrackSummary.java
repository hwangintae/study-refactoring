package org.intaehwang.chapter08.moveFunction;

import lombok.Builder;
import lombok.Getter;

import java.awt.*;

@Getter
public class TrackSummary {
    private double totalTime;
    private double totalDistance;
    private double pace;
    private Point[] points;

    public TrackSummary() {}

    @Builder
    private TrackSummary(double totalTime, double totalDistance, double pace, Point[] points) {
        this.totalTime = totalTime;
        this.totalDistance = totalDistance;
        this.pace = pace;
        this.points = points;
    }

    public TrackSummary create(Point[] points) {
        double totalTime = calculateTime();

        double totalDistance = Distance.calculate(points);

        return TrackSummary.builder()
                .totalTime(totalTime)
                .totalDistance(totalDistance)
                .pace(totalTime / 60 / totalDistance)
                .points(points)
                .build();
    }

    public double calculateTime() {
        return 0;
    }

}
