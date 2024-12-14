package org.group14.services.routing.bus.routing.dijkstra;

import org.group14.services.gtfs.stops.Stop;
import org.group14.domain.models.Coordinate;

import java.time.LocalDateTime;

public interface IDijkstra {
    BusPathResults dijkstra(Stop origin, Stop destination, LocalDateTime startTime);
    BusPathResults dijkstra(Stop origin, Coordinate destination, LocalDateTime startTime);
}
