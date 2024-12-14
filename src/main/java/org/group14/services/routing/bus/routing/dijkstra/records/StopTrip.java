package org.group14.services.routing.bus.routing.dijkstra.records;

import org.group14.services.gtfs.stops.Stop;

public record StopTrip(Stop stop, String tripId, long timeToReach) {
}
