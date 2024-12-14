package org.group14.services.routing.bus.routing.responses;

import javafx.scene.paint.Color;
import org.group14.domain.models.Coordinate;

import java.util.List;

public record RouteItem(List<Coordinate> coordinates, Color routeColor){
}
