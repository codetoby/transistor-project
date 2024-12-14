package org.group14.services.routing.bus.routing.database_query;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.group14.services.datasource.DataSourceFactory;
import org.group14.services.gtfs.shapes.Shape;
import org.group14.services.gtfs.shapes.ShapeService;
import org.group14.services.gtfs.stops.Stop;
import org.group14.services.gtfs.stops.StopService;
import org.group14.services.gtfs.trip.Trip;
import org.group14.services.gtfs.trip.TripService;
import org.group14.domain.exceptions.RouteNotFoundException;
import org.group14.domain.models.Coordinate;
import org.group14.domain.models.Route;
import org.group14.services.routing.bus.routing.database_query.model.PossibleTrip;
import org.group14.services.routing.bus.routing.database_query.model.StopKey;
import org.group14.services.routing.bus.routing.responses.AbstractBusRoutingResponse;
import org.group14.services.routing.bus.routing.responses.DirectBusRoutingResponse;
import org.group14.services.routing.bus.IBusRoutingService;

public class BusRoutingDatabaseService implements IBusRoutingService<Coordinate> {

    private static final Logger LOGGER = Logger.getLogger(BusRoutingDatabaseService.class.getName());
    private final StopService stopService = new StopService();
    private final TripService tripService = new TripService();
    private final ShapeService shapeService = new ShapeService();
    private final IBusRoutingDatabaseRepository possibleTripRepository;
    private static final int RADIUS = 1000;
    private static final int LIMIT = 10;

    public BusRoutingDatabaseService() {
        possibleTripRepository = new BusRoutingDatabaseRepository(DataSourceFactory.createDataSource());
    }

    @Override
    public AbstractBusRoutingResponse getRoute(Coordinate from, Coordinate to) throws RouteNotFoundException{
        return getRoute(from, to, LocalDateTime.now());
    }

    @Override
    public AbstractBusRoutingResponse getRoute(Coordinate origin, Coordinate destination, LocalDateTime time) throws RouteNotFoundException {

        Map<StopKey, PossibleTrip> possibleTrips = new HashMap<>();
        Map<Stop, Double> originStopMap = stopService.getClosetsStops(origin, RADIUS, LIMIT);
        Map<Stop, Double> destStopMap = stopService.getClosetsStops(destination, RADIUS, LIMIT);

        for (Stop originStop : originStopMap.keySet()) {
            for (Stop destStop : destStopMap.keySet()) {
                try {
                    List<PossibleTrip> possibleTripsFromOriginToDestination = possibleTripRepository
                        .findPossibleTrips(originStop, destStop, time.toString(), "20240404", 1);
                    if (!possibleTripsFromOriginToDestination.isEmpty()) {
                        StopKey stopKey = new StopKey(originStop, destStop);
                        // filter possible trips with different route id
                        possibleTrips.put(stopKey, possibleTripsFromOriginToDestination.getFirst());
                    }
                } catch (Exception e) {
                   LOGGER.log(Level.WARNING, "PossibleTripService::findPossibleTrips: " + e.getMessage());
                }
            }
        }

        if (possibleTrips.isEmpty()) {
            throw new RouteNotFoundException();
        }

        PossibleTrip possibleTrip = getShortePossibleTrip(possibleTrips);
        Route<Coordinate> route = getRouteFromPossibleTrip(possibleTrip);
        return new DirectBusRoutingResponse(origin,
                destination,
                possibleTrip.getStartStop(),
                possibleTrip.getEndStop(),
                route);
    }

    private static PossibleTrip getShortePossibleTrip(Map<StopKey, PossibleTrip> possibleTrips) {
        PossibleTrip shortestPossibleTrip = null;
        for (PossibleTrip possibleTrip : possibleTrips.values()) {
            System.out.println(possibleTrip);
            if (shortestPossibleTrip == null || Double.parseDouble(possibleTrip.getTotalDistance()) < Double.parseDouble(shortestPossibleTrip.getTotalDistance())) {
                shortestPossibleTrip = possibleTrip;
            }
        }
        return shortestPossibleTrip;
    }

    private Route<Coordinate> getRouteFromPossibleTrip(PossibleTrip possibleTrip) {      
        Trip trip = tripService.findById(possibleTrip.getTripId());
        Stop startStop = stopService.findById(possibleTrip.getStartStopId());
        Stop endStop = stopService.findById(possibleTrip.getEndStopId());
        List<Shape> shapes = shapeService.findShapesBetweenStops(startStop.getCoordinate(), endStop.getCoordinate(), trip);
        return new Route<>(Coordinate.class, shapes.stream().map(Shape::getCoordinate).toList());
    }
    
}
