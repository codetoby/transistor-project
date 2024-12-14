package org.group14.domain.exceptions;

/**
 * Exception thrown when a route is not found between two zip codes.
 */
public class RouteNotFoundException extends Exception {

    /**
     * Constructor.
     * @param message The message of the exception.
     */
    public RouteNotFoundException() {
        super("Route not found between the two given zip codes");
    }
}
