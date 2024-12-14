package org.group14.domain.models.walking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

import org.group14.domain.models.Coordinate;
import org.group14.domain.models.interfaces.IGraph;

/**
 * This class represents a graph. It contains a list of vertices and an
 * adjacency map.
 * It is used to represent the nodes and edges of the osm map of Maastricht.
 */
@Getter
@AllArgsConstructor
@ToString
public final class WalkingGraph implements IGraph {
    private Map<Integer, Coordinate> vertex;
    private Map<Integer, List<WalkingEdge>> adjacencyList;
}