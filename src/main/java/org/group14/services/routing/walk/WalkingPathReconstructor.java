package org.group14.services.routing.walk;

import org.group14.domain.models.Coordinate;
import org.group14.domain.models.Route;
import org.group14.services.routing.IPathReconstructor;

import lombok.AllArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class WalkingPathReconstructor implements IPathReconstructor<Coordinate> {

    private Map<Integer, Coordinate> coordinates;
    private WalkingPathResults pathResults;
    private int destinationId;

    public Route<Coordinate> reconstructRoute() {
        List<Coordinate> path = new LinkedList<>();

        Integer currentId = destinationId;
        while (currentId != null) {
            Coordinate coordinate = coordinates.get(currentId);
            if (coordinate != null) {
                path.add(0, coordinate); 
            }
            currentId = pathResults.getPrevious().get(currentId);
        }

        return new Route<>(Coordinate.class, path);
    }
}