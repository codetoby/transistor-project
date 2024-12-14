package org.group14.services.gtfs.shapes;

import org.group14.domain.models.Coordinate;

import java.util.List;

public interface IShapeRepository {
    /**
     * Get all shapes
     * @param id The id of the shape
     * @return The list of shapes
     */
    List<Shape> getById(String id);

    /**
     * Find the closest shape sequence to the given coordinate
     * @param coordinate The coordinate to find the closest shape sequence to
     * @param shapeId The id of the shape
     * @return The closest shape sequence
     */
    Shape findClosestShapeSequence(Coordinate coordinate, String shapeId);
}

