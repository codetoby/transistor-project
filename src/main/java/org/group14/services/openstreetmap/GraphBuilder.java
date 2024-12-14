package org.group14.services.openstreetmap;

import com.graphhopper.routing.util.AllEdgesIterator;
import com.graphhopper.storage.GraphHopperStorage;
import com.graphhopper.storage.NodeAccess;

import org.group14.domain.models.Coordinate;
import org.group14.domain.models.walking.WalkingEdge;
import org.group14.domain.models.walking.WalkingGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphBuilder {

    private final GraphHopperStorage storage;
    private final NodeAccess nodeAccess;

    public GraphBuilder(GraphHopperStorage storage) {
        this.storage = storage;
        this.nodeAccess = storage.getNodeAccess();
    }

    public WalkingGraph buildGraph() {
        Map<Integer, Coordinate> coordinates = new HashMap<>();
        Map<Integer, List<WalkingEdge>> adjacencyList = new HashMap<>();
        AllEdgesIterator edgeIterator = storage.getAllEdges();

        while (edgeIterator.next()) {
            int sourceNodeId = edgeIterator.getBaseNode();
            int targetNodeId = edgeIterator.getAdjNode();

            Coordinate source = getOrCreateCoordinate(coordinates, sourceNodeId);
            Coordinate target = getOrCreateCoordinate(coordinates, targetNodeId);

            addEdge(adjacencyList, sourceNodeId, targetNodeId, target, edgeIterator.getDistance());
            addEdge(adjacencyList, targetNodeId, sourceNodeId, source, edgeIterator.getDistance());
        }

        return new WalkingGraph(coordinates, adjacencyList);
    }

    private Coordinate getOrCreateCoordinate(Map<Integer, Coordinate> coordinates, int nodeId) {
        return coordinates.computeIfAbsent(nodeId, id -> {
            double lat = nodeAccess.getLat(id);
            double lon = nodeAccess.getLon(id);
            return new Coordinate(id, lat, lon);
        });
    }

    private void addEdge(Map<Integer, List<WalkingEdge>> adjacencyList, int fromId, int toId, Coordinate target, double distance) {
        WalkingEdge edge = new WalkingEdge(toId, target, distance);
        adjacencyList.computeIfAbsent(fromId, k -> new ArrayList<>()).add(edge);
    }
}