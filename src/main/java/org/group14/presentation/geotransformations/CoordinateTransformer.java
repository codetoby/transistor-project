package org.group14.presentation.geotransformations;

import org.group14.domain.models.Coordinate;
import org.group14.domain.models.Point;
import org.group14.domain.models.Resolution;

/**
 * This class is responsible for transforming coordinates to points on the
 * screen.
 * It uses a reference coordinate and a second reference coordinate to calculate
 * the scaling factors.
 * The scaling factors are used to calculate the x and y values of the point on
 * the screen.
 * The scaling factors are calculated by dividing the screen resolution by the
 * difference between the reference coordinates.
 * The x and y values of the point on the screen are calculated by multiplying
 * the scaling factors with the difference between the reference coordinates and
 * the target coordinate.
 * The x and y values are then returned as a point.
 * 
 */
public class CoordinateTransformer {

    private final Coordinate referenceCoordinate;
    private final Coordinate secondReferenceCoordinate;
    private final Resolution screenResolution;
    private double xScale;
    private double yScale;

    /**
     * Constructor for the CoordinateTransformer class.
     * It takes a reference coordinate, a second reference coordinate and a screen
     * resolution as parameters.
     * It then calls the createScalingFactors method.
     * 
     * @param referenceCoordinate       The reference coordinate.
     * @param secondReferenceCoordinate The second reference coordinate.
     * @param screenResolution          The screen resolution.
     * @return A CoordinateTransformer object.
     */
    public CoordinateTransformer(Coordinate referenceCoordinate, Coordinate secondReferenceCoordinate,
            Resolution screenResolution) {
        this.referenceCoordinate = referenceCoordinate;
        this.secondReferenceCoordinate = secondReferenceCoordinate;
        this.screenResolution = screenResolution;
        createScalingFactors();
    }

    /**
     * This method takes a coordinate as a parameter and returns a point.
     * 
     * @param coordinate The coordinate to be transformed.
     * @return A point on the screen.
     */
    public Point mapCoordinateToPoint(Coordinate coordinate) {
        double latitude = coordinate.getLatitude();
        double longitude = coordinate.getLongitude();

        double deltaLatitude = Math.abs(referenceCoordinate.getLatitude() - latitude);
        double deltaLongitude = Math.abs(referenceCoordinate.getLongitude() - longitude);

        double x = xScale * deltaLongitude;
        double y = yScale * deltaLatitude;

        return new Point(x, y);
    }

    /**
     * This method calculates the scaling factors.
     */
    private void createScalingFactors() {
        xScale = screenResolution.getWidth()
                / (Math.abs(referenceCoordinate.getLongitude() - secondReferenceCoordinate.getLongitude()));
        yScale = screenResolution.getHeight()
                / (Math.abs(referenceCoordinate.getLatitude() - secondReferenceCoordinate.getLatitude()));
    }
}
