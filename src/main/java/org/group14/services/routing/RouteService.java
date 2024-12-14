package org.group14.services.routing;

import java.time.LocalDateTime;

import org.group14.config.ConfigHandler;
import org.group14.domain.exceptions.ConfigNotLoadedException;
import org.group14.domain.exceptions.RouteNotFoundException;
import org.group14.domain.models.Coordinate;
import org.group14.domain.models.ZipCode;
import org.group14.services.routing.bus.BusRoutingEnum;
import org.group14.services.routing.bus.BusRoutingServiceFactory;
import org.group14.services.routing.bus.IBusRoutingService;
import org.group14.services.routing.walk.WalkingRoutingResponse;
import org.group14.services.routing.walk.WalkingRoutingService;

/**
 * Service class for the route functionality.
 */
public class RouteService {

    private static RouteService instance;
  
    private RouteService(ConfigHandler configHandler) throws Exception {
    }

    public static RouteService getInstance() throws Exception {
        if (instance == null) {
            ConfigHandler configHandler = ConfigHandler.getInstance();
            if (configHandler.checkIfRoutingConfigIsLoaded()) {
                throw new ConfigNotLoadedException("Routing config not loaded!");
            }
            instance = new RouteService(configHandler);
        }
        return instance;
    }

    public AbstractRoutingResponse getRoute(Coordinate source, Coordinate destination, BusRoutingEnum busRoutingEnum) throws RouteNotFoundException {
        LocalDateTime time = LocalDateTime.of(2022, 1, 1, 12, 0);
        @SuppressWarnings("rawtypes")
        IBusRoutingService busRoutingService = BusRoutingServiceFactory.getBusRoutingService(busRoutingEnum, time);
        return busRoutingService.getRoute(source, destination, time);
    }

    public AbstractRoutingResponse getRoute(ZipCode source, ZipCode destination, BusRoutingEnum busRoutingEnum) throws RouteNotFoundException {
        return getRoute(source.getCoordinate(), destination.getCoordinate(), busRoutingEnum);
    }

    public WalkingRoutingResponse getRoute(ZipCode source, ZipCode destination) throws RouteNotFoundException {
        WalkingRoutingService walkingRoutingService = new WalkingRoutingService();
        return walkingRoutingService.getRoute(source.getCoordinate(), destination.getCoordinate());
    }

}