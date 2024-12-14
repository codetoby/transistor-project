package org.group14.services.routing.bus.routing.dijkstra.records;

import java.time.LocalDateTime;

import org.group14.services.gtfs.stops.Stop;
import org.group14.domain.models.Coordinate;

public record StopCoordinatePair(Stop originStop , Coordinate destination, LocalDateTime minDepartureTime) {
}
