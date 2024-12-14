package org.group14.services.routing.bus.routing.dijkstra;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.group14.services.gtfs.stops.Stop;
import org.group14.services.gtfs.stops.StopService;
import org.group14.domain.exceptions.RouteNotFoundException;
import org.group14.domain.models.Coordinate;
import org.group14.services.routing.AbstractRoutingResponse;
import org.group14.services.routing.RouteUtils;
import org.group14.services.routing.bus.routing.dijkstra.records.StopCoordinatePair;
import org.group14.services.routing.bus.routing.responses.AbstractBusRoutingResponse;
import org.group14.services.routing.bus.IBusRoutingService;
import org.group14.services.routing.bus.Item;
import org.group14.services.routing.bus.routing.dijkstra.records.PairRoute;
import org.group14.services.routing.bus.routing.responses.BusTransferRoutingResponse;

public class BusRoutingDijkstraService implements IBusRoutingService<Item> {

    private final StopService stopService = new StopService();
    private final IDijkstra dijkstra;

    public BusRoutingDijkstraService(IDijkstra dijkstra) {
        this.dijkstra = dijkstra;
    }

    @Override
    public AbstractRoutingResponse getRoute(Coordinate origin, Coordinate destination) throws RouteNotFoundException {
        return getRoute(origin, destination, LocalDateTime.now());
    }

    @Override
    public AbstractBusRoutingResponse getRoute(Coordinate origin, Coordinate destination, LocalDateTime time) throws RouteNotFoundException{
        Map<Stop, Double> originStopMap = stopService.getClosetsStops(origin, 1, 5);
        Map<Stop, LocalDateTime> originDepartureTimeMap = RouteUtils.getDepartureTimeMap(originStopMap, time);

        List<StopCoordinatePair> stopCoordinatePairs = originStopMap
                .keySet()
                .stream()
                .map(stop -> new StopCoordinatePair(stop, destination, originDepartureTimeMap.get(stop)))
                .toList();

        List<BusPathResults> responses = stopCoordinatePairs
                .stream()
                .map(pair -> dijkstra.dijkstra(pair.originStop(), pair.destination(), pair.minDepartureTime()))
                .filter(Objects::nonNull)
                .toList();

        List<PairRoute> pairRoutes = responses
                .stream()
                .map(response -> PairRoute.fromBusPathResult(response, destination, time))
                .toList();

        if (pairRoutes.isEmpty()) {
            throw new RouteNotFoundException();
        }

        PairRoute bestRoute = pairRoutes.getFirst();

        return new BusTransferRoutingResponse(
                origin,
                destination,
                bestRoute.stopPair().originStop(),
                bestRoute.stopPair().destinationStop(),
                bestRoute.routeItems()
        );
    }

}
