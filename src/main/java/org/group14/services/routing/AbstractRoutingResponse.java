package org.group14.services.routing;

import org.group14.domain.models.Coordinate;

import lombok.Getter;

@Getter
public abstract class AbstractRoutingResponse {

    protected Coordinate origin;
    protected Coordinate destination;

    public AbstractRoutingResponse(Coordinate origin, Coordinate destination) {
        this.origin = origin;
        this.destination = destination;
    }

}