package org.group14.services.calculator;

import lombok.Setter;
import org.group14.domain.models.Coordinate;
import org.group14.services.calculator.distance.DistanceCalculator;
import org.group14.services.calculator.distance.decorator.RoundedDistanceCalculator;
import org.group14.services.calculator.time.TimeCalculator;
import org.group14.services.calculator.time.decorator.RoundedTimeCalculator;
import org.group14.services.calculator.time.decorator.ToMinuteTimeCalculator;


@Setter
public class Measurer {

    private DistanceCalculator distanceCalculator;
    private TimeCalculator timeCalculator;

    public Measurer(DistanceCalculator distanceCalculator, TimeCalculator timeCalculator) {
        this.distanceCalculator = distanceCalculator;
        this.timeCalculator = timeCalculator;
    }

    public double getDistance(Coordinate coordinate1, Coordinate coordinate2) {
        distanceCalculator = new RoundedDistanceCalculator(distanceCalculator);
        return distanceCalculator.calculateDistance(coordinate1, coordinate2);
    }

    public double getTime(double distance) {
        timeCalculator = new ToMinuteTimeCalculator(timeCalculator);
        timeCalculator = new RoundedTimeCalculator(timeCalculator);
        return timeCalculator.calculateTime(distance);
    }

    public double getTimeBetweenCoordinates(Coordinate coordinate1, Coordinate coordinate2) {
        double distance = getDistance(coordinate1, coordinate2);
        return getTime(distance);
    }
}