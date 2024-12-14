package org.group14.services.gtfs.stops;

import lombok.ToString;
import org.group14.domain.models.Coordinate;
import org.group14.domain.models.Point;
import org.group14.domain.models.interfaces.IRouteItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.group14.services.gtfs.GTFSModel;

/**
 * Stop class represents a stop in the GTFS data.
 */
@Getter
@AllArgsConstructor
@ToString
public final class Stop extends GTFSModel implements IRouteItem {

    private String stopId;
    private String stopCode;
    private String stopName;
    private String stopLat;
    private String stopLon;
    private String locationType;
    private String parentStation;
    private String stopTimezone;
    private int wheelchairBoarding;
    private String platformCode;
    private String zoneId;
    private final StopService stopService = new StopService();

    public Stop(String stopId, String stopCode, String stopName, String stopLat, String stopLon) {
        this.stopId = stopId;
        this.stopCode = stopCode;
        this.stopName = stopName;
        this.stopLat = stopLat;
        this.stopLon = stopLon;
    }

    public Stop(String stopId, String stopLat, String stopLon) {
        this.stopId = stopId;
        this.stopLat = stopLat;
        this.stopLon = stopLon;
    }

    public Stop(String stopId) {
        this.stopId = stopId;
    }

    public String getStopName() {
        if (stopName == null)
            stopName = stopService.getNameById(stopId);
        return stopName;
    }

    public Coordinate getCoordinate() {
        return new Coordinate(Double.parseDouble(stopLat), Double.parseDouble(stopLon));
    }

    public Point toPoint() {
        return getCoordinate().toPoint();
    }

    @Override
    public int hashCode() {
        return stopId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Stop stop))
            return false;
        return stopId.equals(stop.getStopId());
    }

}
