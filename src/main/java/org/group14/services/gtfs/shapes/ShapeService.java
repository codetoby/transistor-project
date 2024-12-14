package org.group14.services.gtfs.shapes;

import java.util.List;

import org.group14.services.datasource.DataSourceFactory;
import org.group14.services.gtfs.trip.Trip;
import org.group14.domain.models.Coordinate;
import org.springframework.stereotype.Service;

/**
 * ShapeService class is responsible for handling the business logic for the
 * Shape class.
 */
@Service
public class ShapeService {

    private IShapeRepository shapeRepository;

    /**
     * Constructor for the ShapeService class.
     */
    public ShapeService() {
        this.shapeRepository = new ShapeRepository(DataSourceFactory.createDataSource());
    }

    /**
     * Finds all shapes with the given shape_id.
     */
    public List<Shape> getById(String id) {
        return shapeRepository.getById(id);
    }

    /**
     * Finds the closest shape sequence to the given coordinate.
     */
    public Shape findClosestShapeSequence(Coordinate coordinate, String shapeId) {
        return shapeRepository.findClosestShapeSequence(coordinate, shapeId);
    }

    /**
     * Finds all shapes between the origin and destination stops of a trip.
     */
    public List<Shape> findShapesBetweenStops(Coordinate originCoordinate, Coordinate destinationCoordinate,
            Trip trip) {
        Shape originShape = findClosestShapeSequence(originCoordinate, trip.getShapeId());
        Shape destinationShape = findClosestShapeSequence(destinationCoordinate, trip.getShapeId());

        int originShapeSequence = originShape.getShapePtSequence();
        int destinationShapeSequence = destinationShape.getShapePtSequence();

        List<Shape> shapes = getById(trip.getShapeId());
        shapes.removeIf(shape -> shape.getShapePtSequence() < originShapeSequence
                || shape.getShapePtSequence() > destinationShapeSequence);
        return shapes;
    }
}
