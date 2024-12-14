package org.group14.services.gtfs.shapes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.group14.domain.models.Coordinate;
import org.group14.services.gtfs.GTFSModel;

/**
 * Shape class represents a shape in the GTFS data.
 */
@Getter
@AllArgsConstructor
@ToString
public final class Shape extends GTFSModel {
    private String shapeId;
    private String shapePtLat;
    private String shapePtLon;
    private int shapePtSequence;
    private String shapeDistTraveled;

    /**
     * Get the coordinate of the shape.
     *
     * @return The coordinate of the shape.
     */
    public Coordinate getCoordinate() {
        return new Coordinate(Double.parseDouble(shapePtLat), Double.parseDouble(shapePtLon));
    }
}
