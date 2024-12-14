package org.group14.services.routing.bus;

import java.time.LocalDateTime;

import org.group14.services.routing.bus.routing.database_query.BusRoutingDatabaseService;
import org.group14.services.routing.bus.routing.dijkstra.BusRoutingDijkstraService;
import org.group14.services.routing.bus.routing.dijkstra.v1.BusPathfinder;

public class BusRoutingServiceFactory {
    @SuppressWarnings("rawtypes")
    public static IBusRoutingService getBusRoutingService(BusRoutingEnum busRoutingEnum, LocalDateTime time) {
        return switch (busRoutingEnum) {
            case DATABASE -> new BusRoutingDatabaseService();
            case DIJKSTRA_V1 -> new BusRoutingDijkstraService(new BusPathfinder(time));
        };
    }
}
