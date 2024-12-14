package org.group14.domain.models.interfaces;

import org.group14.domain.models.walking.WalkingEdge;
import org.group14.services.routing.bus.graph.edges.IBusGraphEdge;

public sealed interface IEdge permits WalkingEdge, IBusGraphEdge {
    
}
