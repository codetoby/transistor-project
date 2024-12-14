package org.group14.domain.exceptions;

/**
 * Exception thrown when the configuration file is not loaded.
 */
public class ConfigNotLoadedException extends Exception {

    /**
     * Constructor.
     * @param message The message of the exception.
     */
    public ConfigNotLoadedException(String message) {
        super(message);
    }
}
