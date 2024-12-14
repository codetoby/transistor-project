package org.group14.services.routing;

import org.group14.domain.exceptions.RouteNotFoundException;
import org.group14.domain.models.Coordinate;

public interface IRoutingService {
    AbstractRoutingResponse getRoute(Coordinate origin, Coordinate destination) throws RouteNotFoundException;
}
