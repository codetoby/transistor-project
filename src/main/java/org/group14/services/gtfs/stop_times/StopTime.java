package org.group14.services.gtfs.stop_times;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.group14.services.gtfs.GTFSModel;

/**
 * StopTime class represents a stop_time in the GTFS data.
 */
@Getter
@AllArgsConstructor
@ToString
public class StopTime extends GTFSModel {

    private String tripId;
    private String stopSequence;
    private String stopId;
    private String stopHeadsign;
    private String arrivalTime;
    private String departureTime;
    private int pickupType;
    private int dropOffType;
    private int timepoint;
    private double shapeDistTraveled;
    private String fareUnitsTraveled;

    public StopTime(String tripId, String stopSequence, String stopId, String stopHeadsign, String arrivalTime,
            String departureTime) {
        this.tripId = tripId;
        this.stopSequence = stopSequence;
        this.stopId = stopId;
        this.stopHeadsign = stopHeadsign;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }
}
