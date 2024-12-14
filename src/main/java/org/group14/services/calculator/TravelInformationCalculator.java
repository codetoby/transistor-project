package org.group14.services.calculator;

import org.group14.domain.models.Coordinate;
import org.group14.domain.models.ZipCode;
import org.group14.services.calculator.time.BikingTime;
import org.group14.services.calculator.time.WalkingTime;

/**
 * This class represents the travel information service.
 */
public class TravelInformationCalculator {

    private Measurer measurer;

    /**
     * This is the constructor of the class.
     */
    public TravelInformationCalculator(Measurer measurer) {
        this.measurer = measurer;
    }

    /**
     * This method calculates the walking time to travel a distance.
     * 
     * @param distance The distance to travel.
     * @return The time to travel the distance.
     */
    private double getWalkingTime(double distance) {
        measurer.setTimeCalculator(new WalkingTime());
        return measurer.getTime(distance);
    }

    /**
     * This method calculates the biking time to travel a distance.
     * 
     * @param distance The distance to travel.
     * @return The time to travel the distance.
     */
    private double getBikeTime(double distance) {
        measurer.setTimeCalculator(new BikingTime());
        return measurer.getTime(distance);
    }

    /**
     * This method calculates the aerial distance between two coordinates.
     * 
     * @param coordinate1 The first coordinate.
     * @param coordinate2 The second coordinate.
     * @return The distance between the two coordinates.
     */
    private double getArialDistance(Coordinate coordinate1, Coordinate coordinate2) {
        return measurer.getDistance(coordinate1, coordinate2);
    }

    /**
     * This method calculates the travel information between two zip codes.
     * 
     * @param origin      The origin zip code.
     * @param destination The destination zip code.
     * @param distanceCalculator       The distance calculator.
     * @return The travel information.
     */
    public TravelInformationDTO getTravelInformation(ZipCode origin, ZipCode destination)  {
        double distance = getArialDistance(origin.getCoordinate(), destination.getCoordinate());
        double walkingTime = getWalkingTime(distance * 1000);
        double bikeTime = getBikeTime(distance * 1000);
        return new TravelInformationDTO(origin, destination, walkingTime, bikeTime, distance);

    }
}
