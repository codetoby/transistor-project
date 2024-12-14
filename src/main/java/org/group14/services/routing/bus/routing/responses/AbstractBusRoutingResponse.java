package org.group14.services.routing.bus.routing.responses;

import org.group14.services.gtfs.stops.Stop;
import org.group14.domain.models.Coordinate;
import org.group14.services.routing.AbstractRoutingResponse;
import org.group14.services.routing.walk.WalkingRoutingService;

import java.util.List;

public abstract class AbstractBusRoutingResponse extends AbstractRoutingResponse {

    protected Stop originStop;
    protected Stop destinationStop;

    private final WalkingRoutingService walkingRoutingService = new WalkingRoutingService();

    public AbstractBusRoutingResponse(Coordinate origin, Coordinate destination, Stop originStop, Stop destinationStop) {
        super(origin, destination);

        this.originStop = originStop;
        this.destinationStop = destinationStop;
    }

    public List<Coordinate> getWalkPathFromOriginCoordinateToOriginStop() {
        return walkingRoutingService.getRoute(origin, originStop.getCoordinate()).getRoute().getListOfRouteItem();
    }

    public List<Coordinate> getWalkPathFromDestinationStopToDestinationCoordinate() {
        return walkingRoutingService.getRoute(destinationStop.getCoordinate(), destination).getRoute().getListOfRouteItem();
    }


}
