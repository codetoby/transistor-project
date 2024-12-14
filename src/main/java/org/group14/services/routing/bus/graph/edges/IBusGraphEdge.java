package org.group14.services.routing.bus.graph.edges;

import org.group14.domain.models.interfaces.IEdge;

public sealed interface IBusGraphEdge extends IEdge permits BusGraphEdge, BusGraphWalkingEdge {
}
