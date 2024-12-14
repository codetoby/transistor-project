package org.group14.services.gtfs.stops;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.group14.domain.models.Coordinate;
import org.group14.utilities.Logger;

/**
 * StopRepository class is responsible for handling the database operations for
 * the Stop class.
 */
public class StopRepository implements IStopRepository {

    private static final String STOP_ID_COLUMN = "stop_id";
    private static final String STOP_LAT_COLUMN = "stop_lat";
    private static final String STOP_LON_COLUMN = "stop_lon";
    private static final String STOP_NAME_COLUMN = "stop_name";
    private static final String STOP_CODE_COLUMN = "stop_code";

    private final DataSource dataSource;

    public StopRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Finds the closest stops to a given source coordinate within a given radius.
     * 
     * @param source The source coordinate.
     * @param radius The radius to search for stops.
     * @param limit  The limit of stops to return.
     * @return The closest stops to the given source coordinate within the given
     *         radius.
     */
    public Map<Stop, Double> getClosetsStops(Coordinate source, double radius, int limit) {

        HashMap<Stop, Double> stops = new HashMap<>();

        String select = """
                SELECT
                    stop_id,

                    stop_lat,
                    stop_lon,
                    distance
                FROM (
                    SELECT
                        stop_id,
                        stop_lat,
                        stop_lon,
                        calculate_distance(?, ?, stop_lat, stop_lon) AS distance
                    FROM
                        filtered_bus_stops
                ) AS subquery
                WHERE
                    distance < ?
                ORDER BY distance ASC
                LIMIT ?;
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(select)) {
            insertStmt.setDouble(1, source.getLatitude());
            insertStmt.setDouble(2, source.getLongitude());
            insertStmt.setDouble(3, radius);
            insertStmt.setInt(4, limit);
            ResultSet rs = insertStmt.executeQuery();
            while (rs.next()) {
                stops.put(parseClosetsStops(rs), rs.getDouble("distance"));
            }
        } catch (Exception e) {
            Logger.getInstance().error("StopRepository::getClosestStops: " + e.getMessage());
        }

        return stops;
    }

    private Stop parseClosetsStops(ResultSet rs) {
        try {
            return new Stop(
                    rs.getString(STOP_ID_COLUMN),
                    rs.getString(STOP_LAT_COLUMN),
                    rs.getString(STOP_LON_COLUMN));
        } catch (Exception e) {
            Logger.getInstance().error("StopRepository::parseClosetsStops: " + e.getMessage());
        }
        return null;
    }

    /**
     * Finds a stop by its stop_id.
     * 
     * @param id The stop_id of the stop.
     * @return The stop with the given stop_id.
     */
    public Stop getById(String id) {
        String select = "SELECT * " +
                "FROM stops WHERE stop_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(select)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return parseStop(rs);
            }
        } catch (Exception e) {
            Logger.getInstance().error("StopRepository::findById: " + e.getMessage());
        }

        return null;

    }

    /**
     * Parses a ResultSet object into a Stop object.
     * 
     * @param rs The ResultSet object to be parsed.
     * @return The Stop object parsed from the ResultSet object.
     */
    private Stop parseStop(ResultSet rs) {

        try {
            return new Stop(
                    rs.getString(STOP_ID_COLUMN),
                    rs.getString(STOP_CODE_COLUMN),
                    rs.getString(STOP_NAME_COLUMN),
                    rs.getString(STOP_LAT_COLUMN),
                    rs.getString(STOP_LON_COLUMN),
                    rs.getString("location_type"),
                    rs.getString("parent_station"),
                    rs.getString("stop_timezone"),
                    rs.getInt("wheelchair_boarding"),
                    rs.getString("platform_code"),
                    rs.getString("zone_id"));
        } catch (Exception e) {
            Logger.getInstance().error("StopRepository::parseStop: " + e.getMessage());
        }
        return null;

    }

    /**
     * Finds all stops.
     * 
     * @return All stops.
     */
    public List<Stop> getAll() {

        String select = "SELECT stop_id, stop_code, stop_name, stop_lat, stop_lon FROM filtered_bus_stops;";
        List<Stop> stops = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(select)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                stops.add(parseFindAllStop(rs));
            }
        } catch (Exception e) {
            Logger.getInstance().error("StopRepository::findAll: " + e.getMessage());
        }
        return stops;
    }

    private Stop parseFindAllStop(ResultSet rs) {
        try {
            return new Stop(
                    rs.getString(STOP_ID_COLUMN),
                    rs.getString(STOP_CODE_COLUMN),
                    rs.getString(STOP_NAME_COLUMN),
                    rs.getString(STOP_LAT_COLUMN),
                    rs.getString(STOP_LON_COLUMN));
        } catch (Exception e) {
            Logger.getInstance().error("StopRepository::parseFindAllStop: " + e.getMessage());
        }
        return null;
    }

    public String getNameById(String stopId) {
        String select = "SELECT stop_name FROM stops WHERE stop_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(select)) {
            pstmt.setString(1, stopId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return parseStopName(rs);
            }
        } catch (Exception e) {
            Logger.getInstance().error("StopRepository::findNameById: " + e.getMessage());
        }

        return null;
    }

    private String parseStopName(ResultSet rs) {
        try {
            return rs.getString(STOP_NAME_COLUMN);
        } catch (Exception e) {
            Logger.getInstance().error("StopRepository::parseStopName: " + e.getMessage());
        }
        return null;
    }
}
