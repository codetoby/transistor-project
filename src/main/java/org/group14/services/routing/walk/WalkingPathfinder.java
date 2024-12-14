package org.group14.services.routing.walk;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.group14.domain.models.Coordinate;
import org.group14.domain.models.walking.WalkingEdge;
import org.group14.domain.models.walking.WalkingGraph;
import org.group14.services.routing.RouteUtils;

/**
 * Class for the Dijkstra algorithm.
 */
public class WalkingPathfinder {

    /**
     * Returns the minimum distance between the source and the other vertices.
     * @param graph the graph
     * @param source the source coordinate
     *
     * @return a DijkstraDTO object containing the minimum distance and a map with the shortest path
     */
    public WalkingPathResults calculateShortestPaths(WalkingGraph graph, Coordinate source) {
        
        Map<Integer, Double> distance = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();
        Set<Integer> vertices = graph.getAdjacencyList().keySet();

        int sourceId = RouteUtils.getClosestCoordinateTo(source, graph.getVertex()).getId();

        for (int vertexId : vertices) {
            distance.put(vertexId, Double.MAX_VALUE);
            previous.put(vertexId, null);
        }

        distance.put(sourceId, 0.0);
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingDouble(distance::get));
        queue.add(sourceId);

        while (!queue.isEmpty()) {
            Integer u = queue.poll();
            List<WalkingEdge> edges = graph.getAdjacencyList().get(u);
            if (edges == null) continue;

            for (WalkingEdge edge : edges) {
                int v = edge.getCoordinateId();
                double alt = distance.get(u) + edge.getWeight();
                if (alt < distance.get(v)) {
                    distance.put(v, alt);
                    previous.put(v, u);
                    queue.remove(v);
                    queue.add(v);
                }
            }
        }
        return new WalkingPathResults(distance, previous);
    }
}
