package org.group14.services.routing.walk;

import org.group14.domain.models.Coordinate;
import org.group14.domain.models.walking.WalkingGraph;
import org.group14.services.openstreetmap.OSMParser;
import org.group14.services.routing.IRoutingService;
import org.group14.services.routing.RouteUtils;

/**
 * Facade class for the Dijkstra algorithm.
 */
public class WalkingRoutingService implements IRoutingService {

    private final OSMParser readOSMData;

    public WalkingRoutingService() {
        this.readOSMData = new OSMParser();
    }

    /**
     * Returns the shortest path between the given source and destination, using Dijkstra's algorithm.
     *
     * @param source the source coordinate
     * @param destination the destination coordinate
     * @return the shortest path between the source and destination
     */
    public WalkingRoutingResponse getRoute(Coordinate source, Coordinate destination) {

        WalkingGraph graph = readOSMData.parseGraph();

        WalkingPathfinder dijkstra = new WalkingPathfinder();
        WalkingPathResults dijkstraDTO = dijkstra.calculateShortestPaths(graph, source);

        int destinationId = RouteUtils.getClosestCoordinateTo(destination, graph.getVertex()).getId();

        double distance = dijkstraDTO.getDistance().get(destinationId);

        WalkingPathReconstructor walkingPathReconstructor = new WalkingPathReconstructor(graph.getVertex(), dijkstraDTO, destinationId);
        return new WalkingRoutingResponse(source, destination, walkingPathReconstructor.reconstructRoute(), distance);
    }

}
