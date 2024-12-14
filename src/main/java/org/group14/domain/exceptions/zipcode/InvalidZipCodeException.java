package org.group14.domain.exceptions.zipcode;

/**
 * Exception thrown when a zip code is invalid.
 */
public class InvalidZipCodeException extends ZipCodeException {

    /**
     * Constructor.
     * @param code The invalid zip code.
     */
    public InvalidZipCodeException(String code) {
        super("Invalid zip code: " + code + ". ");
    }
}
