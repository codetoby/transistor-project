package org.group14.domain.exceptions.zipcode;

/**
 * Abstract class for exceptions related to zip codes.
 */
public abstract class ZipCodeException extends Exception {

    /**
     * Constructor.
     * @param message The message of the exception.
     */
    protected ZipCodeException(String message) {
        super(message);
    }
}