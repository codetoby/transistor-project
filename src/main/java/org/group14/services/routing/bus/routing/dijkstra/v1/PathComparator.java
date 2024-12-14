package org.group14.services.routing.bus.routing.dijkstra.v1;

import java.util.Comparator;

import org.group14.domain.models.Coordinate;
import org.group14.services.calculator.distance.DistanceCalculator;
import org.group14.services.routing.bus.routing.dijkstra.records.StopTrip;

public class PathComparator implements Comparator<StopTrip> {
    
    private final DistanceCalculator distanceCalculator;
    private final Coordinate destination;
    private static final double DISTANCE_WEIGHT = 0.5;
    private static final double WAIT_TIME_WEIGHT = 0.2;
    
    public PathComparator(DistanceCalculator distanceCalculator, Coordinate destination) {
        this.destination = destination;
        this.distanceCalculator = distanceCalculator;
    }

    private double distanceTo(StopTrip st, Coordinate destination) {
        return distanceCalculator.calculateDistance(st.stop().getCoordinate(), destination);
    }

    private double findStopTripCost(StopTrip st) {
        // Assuming walking speed of 5 km/h == 5/60 km/m 
        double weightedDistance = distanceTo(st, destination) * DISTANCE_WEIGHT;
        double weightedWaitTime = (5.0/60.0) * st.timeToReach() * WAIT_TIME_WEIGHT;

        return weightedDistance + weightedWaitTime;
    }

    public int compare(StopTrip st1, StopTrip st2) {
        return Double.compare(findStopTripCost(st1), findStopTripCost(st2));
    }
    
}
