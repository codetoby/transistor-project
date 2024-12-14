package org.group14.services.calculator.distance;

import org.group14.domain.models.Coordinate;

public class Pythagorean extends DistanceCalculator {

    @Override
    public double calculateDistance(Coordinate coordinate1, Coordinate coordinate2) {
        double x = coordinate1.getLatitude() - coordinate2.getLatitude();
        double y = coordinate1.getLongitude() - coordinate2.getLongitude();
        return Math.sqrt(Math.pow(x * 111000, 2) + Math.pow(y * 64.6927 * 1000, 2));
    }
}