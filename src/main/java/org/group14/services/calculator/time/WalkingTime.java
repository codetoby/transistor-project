package org.group14.services.calculator.time;

public class WalkingTime extends TimeCalculator {

    private static final double WALKING_SPEED = 1.5;

    @Override
    public double calculateTime(double distance) {
        return distance / WALKING_SPEED;
    }
}