package org.group14.utilities;

/**
 * This class is responsible for logging messages to the console.
 */
public final class Logger {

    // ANSI color escape sequences
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";
    private static int logLevel = 2;

    private static Logger instance;

    private Logger() {
    }

    /**
     * This method returns the instance of the Logger class.
     * If the instance is null, a new instance is created.
     * @return The instance of the Logger class.
     */
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /**
     * This method logs a message to the console.
     * The message is printed in green.
     * @param message The message to be logged.
     */
    public void log(String message) {
        if (logLevel >= 2) {
            System.out.println(GREEN + message + RESET);
        }
    }

    /**
     * This method logs an error message to the console.
     * The message is printed in red.
     * @param message The error message to be logged.
     */
    public void error(String message) {
        if (logLevel >= 1) {
            System.out.println(RED + message + RESET);
        }
    }

    public void setLogLevel(int newLevel) {
        logLevel = newLevel;
    }

}
