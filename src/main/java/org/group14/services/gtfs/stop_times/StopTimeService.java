package org.group14.services.gtfs.stop_times;

import org.group14.services.datasource.DataSourceFactory;
import org.group14.utilities.gtfs.TimeHandler;

import java.time.LocalDateTime;
import java.util.List;

/**
 * StopTimeService class is responsible for handling the business logic for the
 * StopTime class.
 */
public class StopTimeService {

    private final IStopTimeRepository stopTimeRepository;

    public StopTimeService() {
        this.stopTimeRepository = new StopTimeRepository(DataSourceFactory.createDataSource());
    }

    /**
     * Finds the bus stop times for a given bus stop source and destination.
     */
    public List<StopTime> getBuStopTimes(String busStopSource, String busStopDestination, String timeToWalk, LocalDateTime time) {
        timeToWalk = TimeHandler.formatTimeToWalk(time, timeToWalk);
        return stopTimeRepository.getBuStopTimes(busStopSource, busStopDestination, timeToWalk);
    }

    /**
     * Finds the stop times for a given trip_id.
     */
    public List<StopTime> getStopTimesByTripId(String tripId) {
        return stopTimeRepository.getStopTimesByTripId(tripId);
    }

    /**
     * Finds stop times by a trip_id, Date and time.
     */
    public List<StopTime> getStopTimeByTripIdAndDate(String tripId, String date, LocalDateTime startTime) {
        String startTimeStr = TimeHandler.getTime(startTime);
        String maxTimeStr = TimeHandler.getTime(startTime.plusMinutes(30));
        return stopTimeRepository.getStopTimeByTripIdAndDate(tripId, date, startTimeStr, maxTimeStr);
    }

    /**
     * Finds the next bus stop times from a stop at a time and date
     */
    public List<StopTime> getStopTimeByStopAndDatetime(String busStop, String date, LocalDateTime startTime) {
        String startTimeStr = TimeHandler.getTime(startTime);
        String maxTimeStr = TimeHandler.getTime(startTime.plusMinutes(30));
        return stopTimeRepository.getStopTimeByStopAndDatetime(busStop, date, startTimeStr, maxTimeStr);
    }

    /**
     * Finds the next bus stop times from a stop at a time and date
     */
    public List<StopTime> getStopTimeByStopAndDatetime(String busStop, String date, LocalDateTime startTime,
            Long maxMinutes) {
        String startTimeStr = TimeHandler.getTime(startTime);
        String maxTimeStr = TimeHandler.getTime(startTime.plusMinutes(maxMinutes));
        return stopTimeRepository.getStopTimeByStopAndDatetime(busStop, date, startTimeStr, maxTimeStr);
    }

    /**
     * Finds stop times for possible direct trips between originStop and destStop.
     * WARING: This might return trips that go on the opposite direction.
     */
    public List<StopTime> getStopTimesForDirectTrip(String originStop, String destStop, String date,
            LocalDateTime startTime) {
        String startTimeStr = TimeHandler.getTime(startTime);
        String maxTimeStr = TimeHandler.getTime(startTime.plusMinutes(60));
        return stopTimeRepository.getStopTimesForDirectTrip(originStop, destStop, date, startTimeStr, maxTimeStr);
    }

    public StopTime getStopTimeByTripIdAndStopId(String tripId, String stopId) {
        return stopTimeRepository.getStopTimeByTripIdAndStopId(tripId, stopId);
    }
}
