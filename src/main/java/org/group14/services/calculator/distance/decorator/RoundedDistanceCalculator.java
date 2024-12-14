package org.group14.services.calculator.distance.decorator;

import org.group14.domain.models.Coordinate;
import org.group14.services.calculator.distance.DistanceCalculator;
import org.group14.utilities.Round;

public class RoundedDistanceCalculator extends DistanceCalculator {

    private DistanceCalculator distanceCalculator;

    public RoundedDistanceCalculator(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public double calculateDistance(Coordinate origin, Coordinate destination) {
        return Round.toSecondDecimal(distanceCalculator.calculateDistance(origin, destination));
    }
    
}
