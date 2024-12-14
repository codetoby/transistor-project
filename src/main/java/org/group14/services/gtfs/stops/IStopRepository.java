package org.group14.services.gtfs.stops;

import org.group14.domain.models.Coordinate;
import org.group14.services.gtfs.IRepository;

import java.util.Map;

public interface IStopRepository extends IRepository<Stop> {
    Map<Stop, Double> getClosetsStops(Coordinate source, double radius, int limit);
    String getNameById(String stopId);
}
