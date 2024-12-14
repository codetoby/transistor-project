package org.group14.services.routing.walk;

import lombok.Getter;
import org.group14.domain.models.Coordinate;
import org.group14.domain.models.Route;
import org.group14.services.routing.AbstractRoutingResponse;

/**
 * Class for the Dijkstra algorithm response.
 */
@Getter
public class WalkingRoutingResponse extends AbstractRoutingResponse {

    private final Route<Coordinate> route;
    private final double distanceMeters;

    public WalkingRoutingResponse(Coordinate origin, Coordinate destination, Route<Coordinate> routes, double distanceMeters) {
        super(origin, destination);
        this.route = routes;
        this.distanceMeters = distanceMeters;
    }
}
