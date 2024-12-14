package org.group14.services.calculator.time;

public class BikingTime extends TimeCalculator {

    private static final double BIKING_SPEED = 7;

    /**
     * Calculate the time it takes to bike a certain distance.
     * 
     * @param distance The distance to bike (in kilometers).
     * @return The time it takes to bike the distance (in the specified unit).
     */
    @Override
    public double calculateTime(double distance) {
        return (distance) / BIKING_SPEED;
    }
}