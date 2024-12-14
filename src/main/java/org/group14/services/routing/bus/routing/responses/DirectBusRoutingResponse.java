package org.group14.services.routing.bus.routing.responses;

import lombok.Getter;
import org.group14.services.gtfs.stops.Stop;
import org.group14.domain.models.Coordinate;
import org.group14.domain.models.Route;

@Getter
public class DirectBusRoutingResponse extends AbstractBusRoutingResponse {

    Route<Coordinate> route;

    public DirectBusRoutingResponse(Coordinate source, Coordinate destination, Stop originStop, Stop destinationStop, Route<Coordinate> route) {
        super(source, destination, originStop, destinationStop);
        this.route = route;
    }

}
