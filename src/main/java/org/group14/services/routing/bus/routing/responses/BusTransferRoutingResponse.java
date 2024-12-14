package org.group14.services.routing.bus.routing.responses;

import javafx.scene.paint.Color;
import lombok.Getter;

import org.group14.services.gtfs.shapes.Shape;
import org.group14.services.gtfs.shapes.ShapeService;
import org.group14.services.gtfs.stops.Stop;
import org.group14.domain.models.Coordinate;
import org.group14.services.routing.bus.Item;
import org.group14.services.routing.walk.WalkingRoutingService;

import java.util.ArrayList;
import java.util.List;

public class BusTransferRoutingResponse extends AbstractBusRoutingResponse {

    private static final List<Color> colors = List.of(Color.RED, Color.BLACK, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PURPLE, Color.PINK, Color.BROWN, Color.CYAN, Color.GRAY);

    @Getter
    private final List<Item> items;
    private final ShapeService shapeService = new ShapeService();
    private final WalkingRoutingService walkingRoutingService = new WalkingRoutingService();

    public BusTransferRoutingResponse(Coordinate origin, Coordinate destination, Stop originStop, Stop destinationStop, List<Item> items) {
        super(origin, destination, originStop, destinationStop);
        this.items = items.reversed();
    }

    public List<RouteItem> getRoutes() {
        List<RouteItem> routes = new ArrayList<>();
        for (int i = 1; i < items.size(); i++) {
            Item item = items.get(i);
            Stop previousStop = items.get(i - 1).getStop();

            if (item.getTripId() == null) {
                continue;
            }

            if (item.getTripId().equals("walk")) {
                List<Coordinate> walkingRoute = walkingRoutingService
                        .getRoute(previousStop.getCoordinate(), item.getStop().getCoordinate())
                        .getRoute().getListOfRouteItem();
                routes.add(new RouteItem(walkingRoute, Color.ORANGE));
            } else {
                List<Shape> shapes = shapeService
                        .findShapesBetweenStops(
                                previousStop.getCoordinate(),
                                item.getStop().getCoordinate(),
                                item.getTrip());
                routes.add(new RouteItem(shapes.stream().map(Shape::getCoordinate).toList(), colors.get(i % colors.size())));
            }
        }
        return routes;
    }
}
