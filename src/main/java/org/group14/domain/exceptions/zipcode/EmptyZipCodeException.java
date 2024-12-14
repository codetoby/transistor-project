package org.group14.domain.exceptions.zipcode;

/**
 * Exception thrown when a zip code is empty.
 */
public class EmptyZipCodeException extends ZipCodeException {

    /**
     * Constructor. 
     * @param message The message of the exception.
     */
    public EmptyZipCodeException(String message) {
        super("Empty zip code: " + message);
    }

}
