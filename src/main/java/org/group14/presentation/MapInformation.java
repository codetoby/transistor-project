package org.group14.presentation;

import org.group14.domain.models.Coordinate;
import org.group14.domain.models.Resolution;

/**
 * This class is responsible for static map data.
 */
public class MapInformation {

    public static final Coordinate referenceCoordinate;
    public static final Coordinate secondCoordinate;
    public static final Resolution resolution;

    /*
     * Static block to initialize the reference coordinate, second coordinate and
     * resolution.
     */
    static {
        referenceCoordinate = new Coordinate(50.903330, 5.639216);
        secondCoordinate = new Coordinate(50.813557, 5.761434);
        resolution = new Resolution(810, 693);
    }

    private MapInformation() {
    }

}
