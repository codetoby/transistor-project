package org.group14.services.calculator.distance;

import org.group14.domain.models.Coordinate;

public class Haversine extends DistanceCalculator {

    public double calculateDistance(Coordinate coordinate1, Coordinate coordinate2) {
        double latitude1 = Math.toRadians(coordinate1.getLatitude());
        double latitude2 = Math.toRadians(coordinate2.getLatitude());
        double longitude1 = Math.toRadians(coordinate1.getLongitude());
        double longitude2 = Math.toRadians(coordinate2.getLongitude());
        double delta = Math.abs(latitude1 - latitude2);
        double lambda = Math.abs(longitude1 - longitude2);
        double a1 = Math.pow(Math.sin(delta / 2), 2);
        double a2 = Math.cos(latitude1) * Math.cos(latitude2) * Math.pow(Math.sin(lambda / 2), 2);
        return 2 * radius * Math.asin(Math.sqrt(a1 + a2));
    }

}
