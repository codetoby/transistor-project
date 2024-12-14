package org.group14.services.zipcode.api.requests;

import java.time.Duration;
import java.util.Date;

/**
 * This enum represents a request period.
 */
public enum RequestPeriod {
    FIVE_SECONDS (Duration.ofSeconds(5).toMillis(), 1),
    ONE_MINUTE (Duration.ofMinutes(1).toMillis(), 5),
    ONE_HOUR (Duration.ofHours(1).toMillis(), 40),
    ONE_DAY (Duration.ofDays(1).toMillis(), 100);

    private final long periodInMillis;
    private final int limit;

    RequestPeriod(long period, int limit) {
        this.periodInMillis = period;
        this.limit = limit;
    }   

    /**
     * Get the period in milliseconds.
     * @return long
     */
    public long getPeriodInMillis() { return periodInMillis; }

    /**
     * Get the maximum limit.
     * @return
     */
    public int getMaxLimit() { return limit; }

    /**
     * Check if the request record is shorter than the period.
     * @param reqRecord
     * @param currentTime
     * @return
     */
    public boolean isShorterThan(RequestRecord reqRecord, Date currentTime) {
        long diff = currentTime.getTime() - reqRecord.date().getTime();
        return diff <= periodInMillis;
    }

    /**
     * Check if the request record is longer than the period.
     * @param reqRecord
     * @param currentTime
     * @return
     */
    public boolean isLongerThan(RequestRecord reqRecord, Date currentTime) {
        long diff = currentTime.getTime() - reqRecord.date().getTime();
        return diff > periodInMillis;
    }

    /**
     * Check if the limit was exceeded.
     * @param reqHist RequestHistory
     * @return boolean
     */
    public boolean wasLimitExceeded(RequestHistory reqHist) {
        return switch (this) {
            case FIVE_SECONDS -> reqHist.fiveSeconds() >= limit;
            case ONE_MINUTE -> reqHist.oneMinute() >= limit;
            case ONE_HOUR -> reqHist.oneHour() >= limit;
            case ONE_DAY -> reqHist.oneDay() >= limit;
            default -> false;
        };
    }
}
