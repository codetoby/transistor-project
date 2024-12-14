package org.group14.services.routing.bus;

import java.time.LocalDateTime;

import lombok.Getter;
import org.group14.services.gtfs.stops.Stop;
import org.group14.services.gtfs.trip.Trip;
import org.group14.services.gtfs.trip.TripService;
import org.group14.domain.models.interfaces.IRouteItem;

import lombok.ToString;

/**
 * Class to represent a stop and its trip id.
 */
@ToString
@Getter
public final class Item implements IRouteItem {
    private final Stop stop;
    private final String tripId;
	private final LocalDateTime time;
	private String headSign;

	private Trip trip;

	private final TripService tripService = new TripService();

	public Item(Stop stop, String tripId, LocalDateTime time) {
		this.stop = stop;
		this.tripId = tripId;
		this.time = time;
	}

    public Item(Stop stop, LocalDateTime time) {
        this(stop, null, time);
    }

	public Item(Stop stop) {
		this(stop, null, null);
	}

	public Trip getTrip() {
		if (trip == null) {
			trip = tripService.findById(tripId);
		}
		return trip;
	}

	public String getHeadSign() {
		if (headSign == null) {
			headSign = getTrip().getTripHeadsign();
		}
		return headSign;
	}

	@Override
	public int hashCode() {
		return stop.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Item item)) {
			return false;
		}
        return stop.equals(item.getStop());
	}
}
