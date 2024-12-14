package org.group14.services.gtfs.stop_times;

import java.util.List;

import org.group14.services.gtfs.IRepository;

public interface IStopTimeRepository extends IRepository<StopTime> {
    List<StopTime> getBuStopTimes(String busStopSource, String busStopDestination, String timeToWalk);
    List<StopTime> getStopTimesByTripId(String tripId);
    StopTime getStopTimeByTripIdAndStopId(String tripId, String stopId);
    List<StopTime> getStopTimeByTripIdAndDate(String tripId, String date, String startTime, String maxTime);
    List<StopTime> getStopTimeByStopAndDatetime(String busStop, String date, String startTime, String maxTime);
    List<StopTime> getStopTimesForDirectTrip(String originStop, String destStop, String date, String startTime, String maxTime);
}