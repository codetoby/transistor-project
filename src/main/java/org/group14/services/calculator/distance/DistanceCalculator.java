package org.group14.services.calculator.distance;

import org.group14.domain.models.Coordinate;

public abstract class DistanceCalculator {

    /**
     * The radius of the Earth in kilometers.
     */
    protected int radius = 6371;

    /**
     * Calculate the distance between two coordinates.
     * 
     * @param origin The origin coordinate.
     * @param destination The destination coordinate.
     * @return The distance between the two coordinates.
     */
    public abstract double calculateDistance(Coordinate origin, Coordinate destination);
}