package org.group14.services.openstreetmap;

import com.graphhopper.GraphHopper;
import com.graphhopper.reader.osm.GraphHopperOSM;
import com.graphhopper.routing.util.EncodingManager;

public class GraphHopperConfig {

    private static final String PATH_TO_OSM_FILE = "src/main/resources/org/group14/Maastricht.osm.pbf";
    private static final String PATH_TO_GRAPHHOPPER_WORKINGDIR = "src/main/resources/org/group14/graphhopper";

    public static GraphHopper configureHopper() {
        GraphHopper hopper = new GraphHopperOSM().forServer();
        hopper.setDataReaderFile(PATH_TO_OSM_FILE);
        hopper.setGraphHopperLocation(PATH_TO_GRAPHHOPPER_WORKINGDIR);
        hopper.setEncodingManager(new EncodingManager("car,bike"));
        hopper.importOrLoad();
        return hopper;
    }
}