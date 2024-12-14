package org.group14.services.calculator.distance;

import org.group14.domain.models.Coordinate;

public class GreatCircle extends DistanceCalculator {

    public double calculateDistance(Coordinate coordinate1, Coordinate coordinate2) {
        double latitude1 = Math.toRadians(coordinate1.getLatitude());
        double latitude2 = Math.toRadians(coordinate2.getLatitude());
        double longitude1 = Math.toRadians(coordinate1.getLongitude());
        double longitude2 = Math.toRadians(coordinate2.getLongitude());
        return radius * Math.acos(Math.sin(latitude1) * Math.sin(latitude2)
                + Math.cos(latitude1) * Math.cos(latitude2) * Math.cos(longitude1 - longitude2));
    }

}