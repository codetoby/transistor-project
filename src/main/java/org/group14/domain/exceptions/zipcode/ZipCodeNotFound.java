package org.group14.domain.exceptions.zipcode;

/**
 * Exception thrown when a zip code is not found.
 */
public class ZipCodeNotFound extends ZipCodeException {

    /**
     * Constructor.
     * @param code The zip code that was not found.
     */
    public ZipCodeNotFound(String code) {
        super("Zip code not found: " + code);
    }
}
