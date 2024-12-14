package org.group14.services.gtfs.stops;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.group14.domain.models.Coordinate;
import org.group14.services.datasource.DataSourceFactory;

/**
 * StopService class is responsible for handling the business logic for the Stop
 * class.
 */
public class StopService {

    private final IStopRepository stopRepository;
    private final static Map<String, Stop> stops;

    static {
        stops = new HashMap<>();
    }

    public StopService() {
        this.stopRepository = new StopRepository(DataSourceFactory.createDataSource());
    }

    public Map<Stop, Double> getClosetsStops(Coordinate source, double radius, int limit) {
        return stopRepository.getClosetsStops(source, radius, limit);
    }

    public List<Stop> findAll() {
        return stopRepository.getAll();
    }

    public Stop findById(String stopId) {
        if (!stops.containsKey(stopId)) {
            stops.put(stopId, stopRepository.getById(stopId));
        }
        return stops.get(stopId);
    }

    public Map<Stop, Map<Stop, Double>> getAllClosestStops(Set<Stop> stops, double radius, int limit) {
        Map<Stop, Map<Stop, Double>> allClosestStops = new HashMap<>();

        for (Stop stop : stops) {
            Map<Stop, Double> closestStops = getClosetsStops(stop.getCoordinate(), radius, limit);
            allClosestStops.put(stop, closestStops);
        }

        return allClosestStops;
    }

    public String getNameById(String stopId) {
        return stopRepository.getNameById(stopId);
    }

}
