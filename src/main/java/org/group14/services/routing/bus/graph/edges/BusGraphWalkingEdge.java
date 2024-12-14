package org.group14.services.routing.bus.graph.edges;

import org.group14.services.gtfs.stops.Stop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Class represents an edge in the bus graph.
 */
@Getter
@AllArgsConstructor
@ToString
public final class BusGraphWalkingEdge implements IBusGraphEdge {

    private Stop fromStop;
    private Stop toStop;
    private double timeToTravel;
    
}
