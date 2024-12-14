package org.group14.services.zipcode.api.requests;

import java.util.Date;

/**
 * This class represents a request record.
 */
public record RequestRecord(Date date, String ip) {}
