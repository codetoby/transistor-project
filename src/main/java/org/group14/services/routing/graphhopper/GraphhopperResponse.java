package org.group14.services.routing.graphhopper;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import org.group14.domain.models.Coordinate;
import org.group14.domain.models.Route;
import org.group14.services.routing.AbstractRoutingResponse;

import com.graphhopper.util.PointList;

/**
 * This class represents the data of a route.
 */
@Getter
public class GraphhopperResponse extends AbstractRoutingResponse {

    private final PointList pointList;
    private final double distance;
    private final long timeInMs;

    public GraphhopperResponse(Coordinate origin, Coordinate destination, PointList pointList, double distance, long timeInMs) {
        super(origin, destination);
        this.pointList = pointList;
        this.distance = distance;
        this.timeInMs = timeInMs;
    }

    public Route<Coordinate> getRoute() {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < pointList.size(); i++) {
            coordinates.add(new Coordinate(pointList.getLatitude(i), pointList.getLongitude(i)));
        }
        return new Route<>(Coordinate.class, coordinates);
    }

}
