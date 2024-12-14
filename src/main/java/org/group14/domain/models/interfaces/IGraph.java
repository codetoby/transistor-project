package org.group14.domain.models.interfaces;

import org.group14.domain.models.walking.WalkingGraph;
import org.group14.services.routing.bus.graph.BusGraph;

/**
 * Interface for the graph.
 */
public sealed interface IGraph permits BusGraph, WalkingGraph {}