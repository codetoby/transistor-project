package org.group14.services.zipcode.api.requests;

/**
 * This class represents a request history.
 */
public record RequestHistory(int fiveSeconds, int oneMinute, int oneHour, int oneDay) {}
