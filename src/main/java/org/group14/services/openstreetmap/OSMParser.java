package org.group14.services.openstreetmap;

import org.group14.domain.models.walking.WalkingGraph;

import com.graphhopper.GraphHopper;
import com.graphhopper.storage.GraphHopperStorage;

public class OSMParser {

    private GraphHopper hopper;

    public WalkingGraph parseGraph() {
        try {
            hopper = GraphHopperConfig.configureHopper();
            GraphHopperStorage storage = hopper.getGraphHopperStorage();
            GraphBuilder builder = new GraphBuilder(storage);
            return builder.buildGraph();
        } catch (Exception e) {
            throw new RuntimeException("Error parsing OpenStreetMap data", e);
        }
    }

    public void shutdown() {
        if (hopper != null) {
            hopper.close();
        }
    }
}