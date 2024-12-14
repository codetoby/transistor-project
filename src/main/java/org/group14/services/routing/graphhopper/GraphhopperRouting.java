package org.group14.services.routing.graphhopper;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;

//import org.apache.log4j.BasicConfigurator;
import org.group14.domain.exceptions.RouteNotFoundException;
import org.group14.domain.models.Coordinate;
import org.group14.services.routing.IRoutingService;
import org.group14.utilities.Logger;

import com.graphhopper.PathWrapper;
import com.graphhopper.reader.osm.GraphHopperOSM;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.PointList;

/**
 * This class represents the route engine.
 */
public class GraphhopperRouting implements IRoutingService {

    private static final Logger logger = Logger.getInstance();
    private static final String PATH_TO_OSM_FILE = "resources/Maastricht.osm.pbf";
    private static final String PATH_TO_GRAPHHOPPER_WORKINGDIR = "src/main/resources/org/group14/graphhopper";
    private final static GraphHopper hopper;

    static {
//        BasicConfigurator.configure();
        hopper = new GraphHopperOSM().forServer();
        hopper.setDataReaderFile(PATH_TO_OSM_FILE);
        hopper.setGraphHopperLocation(PATH_TO_GRAPHHOPPER_WORKINGDIR);
        hopper.setEncodingManager(new EncodingManager("car,bike"));
        hopper.importOrLoad();
    }

    /**
     * This method generates a route between two zip codes.
     * 
     * @param origin      The origin zip code.
     * @param destination The destination zip code.
     * @return The route data.
     */
    public GraphhopperResponse getRoute(Coordinate origin, Coordinate destination) throws RouteNotFoundException {

        GHRequest req = new GHRequest(origin.getLatitude(),
                origin.getLongitude(),
                destination.getLatitude(), destination.getLongitude())
                .setWeighting("fastest")
                .setVehicle("car");

        GHResponse rsp = hopper.route(req);

        if (rsp.hasErrors()) {
            logger.error("GraphhopperRouting::getRoute: " + rsp.getErrors());
            return null;
        } else {
            PathWrapper path = rsp.getBest();
            PointList pointList = path.getPoints();
            double distance = path.getDistance();
            long timeInMs = path.getTime();

            return new GraphhopperResponse(origin, destination, pointList, distance, timeInMs);
        }
    }

}
