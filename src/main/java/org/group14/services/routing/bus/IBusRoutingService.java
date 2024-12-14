package org.group14.services.routing.bus;

import org.group14.domain.models.interfaces.IRouteItem;

import java.time.LocalDateTime;

import org.group14.domain.exceptions.RouteNotFoundException;
import org.group14.domain.models.Coordinate;
import org.group14.services.routing.IRoutingService;
import org.group14.services.routing.bus.routing.responses.AbstractBusRoutingResponse;

public interface IBusRoutingService<T extends IRouteItem> extends IRoutingService {
    AbstractBusRoutingResponse getRoute(Coordinate origin, Coordinate destination, LocalDateTime time) throws RouteNotFoundException;
}
