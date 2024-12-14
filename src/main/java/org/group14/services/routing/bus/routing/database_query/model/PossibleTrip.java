package org.group14.services.routing.bus.routing.database_query.model;

import org.group14.services.gtfs.stops.Stop;
import org.group14.services.gtfs.stops.StopService;
import org.group14.services.gtfs.trip.Trip;
import org.group14.services.gtfs.trip.TripService;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class PossibleTrip {

    private final String tripId;
    private final String startStopId;
    private final String departureTime;
    private final String endStopId;
    private final String arrivalTime;
    private final String totalTime;
    private final String totalDistance;

    public PossibleTrip(String tripId, String startStopId, String departureTime, String endStopId, String arrivalTime, String totalTime, String totalDistance) {
        this.tripId = tripId;
        this.startStopId = startStopId;
        this.departureTime = departureTime;
        this.endStopId = endStopId;
        this.arrivalTime = arrivalTime;
        this.totalTime = totalTime;
        this.totalDistance = totalDistance;
    }

    private final TripService tripService = new TripService();
    private Trip trip;

    public Trip getTrip() {
        if (trip == null) {
            trip = tripService.findById(tripId);
        }
        return trip;
    }

    private final StopService stopService = new StopService();
    private Stop startStop;

    public Stop getStartStop() {
        if (startStop == null) {
            startStop = stopService.findById(startStopId);
        }
        return startStop;
    }

    private Stop endStop;
    public Stop getEndStop() {
        if (endStop == null) {
            endStop = stopService.findById(endStopId);
        }
        return endStop;
    }
}
