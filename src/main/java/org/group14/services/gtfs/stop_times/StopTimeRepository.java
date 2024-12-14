package org.group14.services.gtfs.stop_times;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.group14.utilities.Logger;

/**
 * StopTimeRepository class is responsible for handling the database operations
 * for the StopTime class.
 */
public class StopTimeRepository implements IStopTimeRepository {

    private final DataSource dataSource;

    public StopTimeRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Finds the bus stop times for a given bus stop source and destination.
     *
     * @param busStopSource      The bus stop source.
     * @param busStopDestination The bus stop destination.
     * @param timeToWalk         The time to walk to the bus stop.
     * @return The bus stop times for the given bus stop source and destination.
     */
    public List<StopTime> getBuStopTimes(String busStopSource, String busStopDestination, String timeToWalk) {
        List<StopTime> routes = new ArrayList<>();

        String sql = """
                    SELECT *
                    FROM filtered_stop_times
                    WHERE trip_id IN (
                        SELECT trips.trip_id
                        FROM filtered_trips
                        JOIN stop_times ON stop_times.trip_id = trips.trip_id
                        JOIN stops ON stops.stop_id = stop_times.stop_id
                        WHERE stops.stop_id = ?
                    )
                    AND stop_id = ?
                    AND arrival_time > ?
                    ORDER BY arrival_time
                    LIMIT 5;
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, busStopSource);
            pstmt.setString(2, busStopDestination);
            pstmt.setString(3, timeToWalk);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    routes.add(parseStopTime(rs));
                }
            }
        } catch (Exception e) {
            Logger.getInstance().error("StopTimeRepository::getBuStopTimes: " + e.getMessage());
        }
        return routes;
    }

    /**
     * Finds the bus stop times for a given trip_id.
     *
     * @param tripId The trip_id of the bus stop times.
     * @return The bus stop times for the given trip_id.
     */
    public List<StopTime> getStopTimesByTripId(String tripId) {
        List<StopTime> routes = new ArrayList<>();

        String sql = """
                    SELECT *
                    FROM filtered_stop_times
                    WHERE trip_id = ?
                    ORDER BY stop_sequence;
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tripId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    routes.add(parseStopTime(rs));
                }
            }
        } catch (Exception e) {
            Logger.getInstance().error("StopTimeRepository::getStopTimesByTripId: " + e.getMessage());
        }
        return routes;
    }

    /**
     * Finds the stop time for a specific trip_id and stop_id.
     *
     * @param tripId The trip_id.
     * @param stopId The stop_id.
     * @return The matching StopTime.
     */
    public StopTime getStopTimeByTripIdAndStopId(String tripId, String stopId) {
        String sql = """
                    SELECT *
                    FROM filtered_stop_times
                    WHERE trip_id = ? AND stop_id = ?;
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tripId);
            pstmt.setString(2, stopId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return parseStopTime(rs);
                }
            }
        } catch (Exception e) {
            Logger.getInstance().error("StopTimeRepository::getStopTimeByTripIdAndStopId: " + e.getMessage());
        }
        return null;
    }

    // --------------------------------------
    // Specialized Queries
    // --------------------------------------

    /**
     * Finds stop times for a specific trip_id within a date range.
     */
    public List<StopTime> getStopTimeByTripIdAndDate(String tripId, String date, String startTime, String maxTime) {
        List<StopTime> routes = new ArrayList<>();

        String sql = """
                    SELECT *
                    FROM filtered_stop_times fst
                    WHERE trip_id = ?
                      AND arrival_time > ?
                      AND arrival_time < ?
                      AND EXISTS (
                          SELECT 1
                          FROM trips t
                          JOIN calendar_dates cd ON t.service_id = cd.service_id
                          WHERE fst.trip_id = t.trip_id AND cd.date = ?
                      )
                    ORDER BY arrival_time;
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tripId);
            pstmt.setString(2, startTime);
            pstmt.setString(3, maxTime);
            pstmt.setString(4, date);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    routes.add(parseStopTime(rs));
                }
            }
        } catch (Exception e) {
            Logger.getInstance().error("StopTimeRepository::getStopTimeByTripIdAndDate: " + e.getMessage());
        }
        return routes;
    }

    /**
     * Finds next bus stop times from a stop, given a date and time range.
     */
    public List<StopTime> getStopTimeByStopAndDatetime(String busStop, String date, String startTime, String maxTime) {
        List<StopTime> routes = new ArrayList<>();

        String sql = """
                WITH trips_by_date AS (
                    SELECT DISTINCT route_id, trip_id, direction_id
                    FROM filtered_trips
                    INNER JOIN calendar_dates USING(service_id)
                    WHERE date = ?
                ),
                stop_times_by_datetime AS (
                    SELECT trip_id, stop_id, stop_sequence, arrival_time, departure_time, stop_headsign
                    FROM filtered_stop_times
                    WHERE arrival_time >= ? AND arrival_time < ?
                )
                SELECT stbd.*
                FROM stop_times_by_datetime stbd
                JOIN trips_by_date tbd ON stbd.trip_id = tbd.trip_id
                WHERE stbd.trip_id IN (
                    SELECT trip_id
                    FROM stop_times_by_datetime
                    WHERE stop_id = ?
                )
                ORDER BY stbd.trip_id, stbd.stop_sequence;
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date);
            pstmt.setString(2, startTime);
            pstmt.setString(3, maxTime);
            pstmt.setString(4, busStop);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    routes.add(parseStopTime(rs));
                }
            }
        } catch (Exception e) {
            Logger.getInstance().error("StopTimeRepository::getStopTimeByStopAndDatetime: " + e.getMessage());
        }
        return routes;
    }

    /**
     * Finds direct trips between an origin and destination stop within a date and
     * time range.
     *
     * @param originStop The origin stop ID.
     * @param destStop   The destination stop ID.
     * @param date       The date of the trip.
     * @param startTime  The start time to consider.
     * @param maxTime    The end time to consider.
     * @return A list of StopTime objects representing direct trips.
     */
    public List<StopTime> getStopTimesForDirectTrip(String originStop, String destStop, String date, String startTime,
            String maxTime) {
        List<StopTime> routes = new ArrayList<>();

        String sql = """
                    WITH
                        trips_by_date AS (
                            SELECT route_id, trip_id, direction_id
                            FROM filtered_trips
                            INNER JOIN calendar_dates USING(service_id)
                            WHERE date = ?
                        ),
                        stop_times_by_datetime AS (
                            SELECT trip_id, stop_id, stop_sequence, arrival_time, departure_time, stop_headsign
                            FROM filtered_stop_times
                            INNER JOIN trips_by_date USING(trip_id)
                            WHERE arrival_time >= ? AND arrival_time < ?
                        ),
                        trips_with_stop AS (
                            SELECT DISTINCT trip_id
                            FROM stop_times_by_datetime
                            WHERE stop_id = ? AND trip_id IN (
                                SELECT DISTINCT trip_id
                                FROM stop_times_by_datetime
                                WHERE stop_id = ?
                            )
                        )
                    SELECT *
                    FROM stop_times_by_datetime
                    WHERE trip_id IN (SELECT trip_id FROM trips_with_stop)
                    ORDER BY trip_id, stop_sequence ASC;
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date);
            pstmt.setString(2, startTime);
            pstmt.setString(3, maxTime);
            pstmt.setString(4, originStop);
            pstmt.setString(5, destStop);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    routes.add(parseStopTime(rs));
                }
            }
        } catch (Exception e) {
            Logger.getInstance().error("StopTimeRepository::getStopTimesForDirectTrip: " + e.getMessage());
        }

        return routes;
    }

    /**
     * Parses a ResultSet into a StopTime object.
     *
     * @param rs The ResultSet object.
     * @return The parsed StopTime object.
     */
    private StopTime parseStopTime(ResultSet rs) {
        try {
            return new StopTime(
                    rs.getString("trip_id"),
                    rs.getString("stop_sequence"),
                    rs.getString("stop_id"),
                    rs.getString("stop_headsign"),
                    rs.getString("arrival_time"),
                    rs.getString("departure_time"));
        } catch (Exception e) {
            Logger.getInstance().error("StopTimeRepository::parseStopTime: " + e.getMessage());
            return null;
        }
    }

    public StopTime getById(String id) {
        String sql = """
                    SELECT *
                    FROM filtered_stop_times
                    WHERE trip_id = ?;
                """;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return parseStopTime(resultSet);
            }
        } catch (Exception e) {
            Logger.getInstance().error("StopTimeRepository::findById: " + e.getMessage());
        }
        return null;
    }

    public List<StopTime> getAll() {
        List<StopTime> stopTimes = new ArrayList<>();
        String sql = """
                    SELECT *
                    FROM filtered_stop_times;
                """;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stopTimes.add(parseStopTime(resultSet));
            }
        } catch (Exception e) {
            Logger.getInstance().error("StopTimeRepository::getAll: " + e.getMessage());
        }
        return stopTimes;
    }
}