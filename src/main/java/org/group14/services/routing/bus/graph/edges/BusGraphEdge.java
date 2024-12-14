package org.group14.services.routing.bus.graph.edges;

import java.time.LocalDateTime;

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
public final class BusGraphEdge implements IBusGraphEdge {

    private String tripId;
    private Stop fromStop;
    private Stop toStop;
    private Integer stopSequence;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double timeToTravel;

}
