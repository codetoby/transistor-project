package org.group14.domain.models;

import org.group14.domain.models.interfaces.IRouteItem;
import org.group14.presentation.MapInformation;
import org.group14.presentation.geotransformations.CoordinateTransformer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * This class represents a coordinate.
 */
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public final class Coordinate implements IRouteItem {

    private int id;
    private double latitude;
    private double longitude;

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Point toPoint() {
        return new CoordinateTransformer(
                MapInformation.referenceCoordinate,
                MapInformation.secondCoordinate,
                MapInformation.resolution).mapCoordinateToPoint(this);
    }

}
