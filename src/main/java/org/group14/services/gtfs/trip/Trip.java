package org.group14.services.gtfs.trip;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.group14.services.gtfs.GTFSModel;

/**
 * Represents a trip in the GTFS data.
 */
@AllArgsConstructor
@Getter
@ToString
public class Trip extends GTFSModel {

    private String routeId;
    private String serviceId;
    private String tripId;
    private String realtimeTripId;
    private String tripHeadsign;
    private String tripShortName;
    private String tripLongName;
    private int directionId;
    private String blockId;
    private String shapeId;
    private int wheelchairAccessible;
    private int bikesAllowed;

    public Trip(String routeId, String serviceId, String tripId, String tripHeadsign, String shapeId) {
        this.routeId = routeId;
        this.serviceId = serviceId;
        this.tripId = tripId;
        this.tripHeadsign = tripHeadsign;
        this.shapeId = shapeId;
    }

}
