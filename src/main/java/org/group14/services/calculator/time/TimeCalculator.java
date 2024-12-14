package org.group14.services.calculator.time;

public abstract class TimeCalculator {

    /**
     * Calculate the time it takes to travel a certain distance in the given unit.
     *
     * @param distance The distance in meters.
     * @return The time it takes to travel the distance in the given unit.
     */
    public abstract double calculateTime(double distance);

}
