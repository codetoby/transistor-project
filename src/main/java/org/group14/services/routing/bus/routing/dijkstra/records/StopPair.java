package org.group14.services.routing.bus.routing.dijkstra.records;

import java.time.LocalDateTime;

import org.group14.services.gtfs.stops.Stop;

public record StopPair(Stop originStop , Stop destinationStop, LocalDateTime departureTime) {
}
