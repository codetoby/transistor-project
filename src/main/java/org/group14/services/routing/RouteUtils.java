package org.group14.services.routing;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.group14.services.gtfs.stops.Stop;
import org.group14.domain.models.Coordinate;
import org.group14.services.calculator.distance.DistanceCalculator;
import org.group14.services.calculator.distance.Haversine;
import org.group14.services.calculator.time.TimeCalculator;
import org.group14.services.calculator.time.TimeCalculatorFactory;
import org.group14.services.calculator.time.WalkingTime;

/**
 * Utility class for route-related functionality.
 */
public class RouteUtils {

    private static final DistanceCalculator distanceCalculator = new Haversine();
    private static final TimeCalculator timeCalculator = TimeCalculatorFactory.createCalculator(new WalkingTime());

    private RouteUtils() {
    }

    public static Coordinate getClosestCoordinateTo(Coordinate coordinate, Map<Integer, Coordinate> vertices) {
        Coordinate closest = null;

        double minDistance = Double.MAX_VALUE;
        for (Coordinate vertex : vertices.values()) {
            double distance = getDistance(coordinate, vertex);
            if (distance < minDistance) {
                minDistance = distance;
                closest = vertex;
            }
        }
        return closest;
    }

    public static double getDistance(Coordinate c1, Coordinate c2) {
        return distanceCalculator.calculateDistance(c1, c2);
    }

    public static Map<Stop, LocalDateTime> getDepartureTimeMap(Map<Stop, Double> stopMap, LocalDateTime currentTime) {
        return stopMap.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Entry::getKey,
                        e -> currentTime.plusMinutes((long) timeCalculator.calculateTime(e.getValue() * 1000))));
    }

}
