package org.group14.services.routing.walk;

import java.util.List;
import java.util.Map;

/**
 * Data transfer object for the Dijkstra algorithm.
 */
public class WalkingPathResults {

    private Map<Integer, Double> distance;
    private Map<Integer, Integer> previous;

    /**
     * Constructs a new DijkstraDTO object.
     *
     * @param distance the map of distances
     * @param previous the map of previous vertices
     */
    public WalkingPathResults(Map<Integer, Double> distance, Map<Integer, Integer> previous) {
        this.distance = distance;
        this.previous = previous;
    }

    public Map<Integer, Double> getDistance() {
        return distance;
    }

    public Map<Integer, Integer> getPrevious() {
        return previous;
    }

    public List<Integer> getVertices() {
        return List.copyOf(distance.keySet());
    }

}
