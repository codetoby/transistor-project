package org.group14.services.routing.bus.routing.dijkstra.records;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.group14.services.gtfs.stops.Stop;
import org.group14.domain.models.Coordinate;
import org.group14.domain.models.Route;
import org.group14.services.calculator.distance.DistanceCalculator;
import org.group14.services.calculator.distance.Haversine;
import org.group14.services.calculator.time.TimeCalculator;
import org.group14.services.calculator.time.TimeCalculatorFactory;
import org.group14.services.calculator.time.WalkingTime;
import org.group14.services.routing.bus.Item;
import org.group14.services.routing.bus.routing.dijkstra.BusPathReconstructor;
import org.group14.services.routing.bus.routing.dijkstra.BusPathResults;

public record PairRoute(StopPair stopPair, List<Item> routeItems, Long distance, int transferCount, double closestDistance) {

    private static final TimeCalculator timeCalculator = TimeCalculatorFactory.createCalculator(new WalkingTime());
    private static final DistanceCalculator distanceCalculator = new Haversine();

    public static PairRoute fromBusPathResult(BusPathResults busPathResults, Coordinate destinationCoordinate, LocalDateTime time) {

        Long walkTimeFromLastStopToDestination = walkTimeToDestination(busPathResults.getLastStop().getCoordinate(), destinationCoordinate);
        Long distance = distanceToEpochSecond(busPathResults) + walkTimeFromLastStopToDestination;

        BusPathReconstructor busPathReconstructor = new BusPathReconstructor(busPathResults);
        Route<Item> route = busPathReconstructor.reconstructRoute();

        int transfers = route.getListOfRouteItem()
                .stream()
                .map(Item::getTripId)
                .collect(Collectors.toSet())
                .size();

        StopPair stopPair = new StopPair(
                busPathResults.getFirstStop(),
                busPathResults.getLastStop(),
                time
        );
        return new PairRoute(stopPair, route.getListOfRouteItem(), distance, transfers, busPathResults.getClosestDistance());
    }

    private static Long walkTimeToDestination(Coordinate destination, Coordinate lastStopCoordinate) {
        double meters = distanceCalculator.calculateDistance(destination, lastStopCoordinate);
        return (long) timeCalculator.calculateTime(meters);
    }

    private static Long distanceToEpochSecond(BusPathResults busPathResults) {
        Map<Stop, LocalDateTime> distMap = busPathResults.getDistance();
        return distMap.get(busPathResults.getLastStop()).atZone(ZoneId.systemDefault()).toEpochSecond();
    }
}