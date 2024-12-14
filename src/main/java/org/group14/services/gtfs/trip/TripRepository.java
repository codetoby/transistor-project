package org.group14.services.gtfs.trip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.group14.utilities.Logger;

/**
 * TripRepository is responsible for handling the database operations for the
 * Trip class.
 */
public class TripRepository implements ITripRepository {

    private final DataSource dataSource;

    public TripRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Finds a trip by its ID.
     * 
     * @param tripId The trip ID.
     * @return The trip with the given ID.
     */
    public Trip getById(String tripId) {

        String select = "SELECT * " + """
                FROM
                    filtered_trips
                WHERE
                    trip_id = ?;
                """;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(select)) {
            pstmt.setString(1, tripId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return parseTrip(rs);
            }
        } catch (Exception e) {
            Logger.getInstance().error("TripRepository::findById: " + e.getMessage());
        }

        return null;

    }

    private Trip parseTrip(ResultSet rs) throws Exception {
        return new Trip(
                rs.getString("route_id"),
                rs.getString("service_id"),
                rs.getString("trip_id"),
                rs.getString("realtime_trip_id"),
                rs.getString("trip_headsign"),
                rs.getString("trip_short_name"),
                rs.getString("trips_long_name"),
                rs.getInt("direction_id"),
                rs.getString("block_id"),
                rs.getString("shape_id"),
                rs.getInt("wheelchair_accessible"),
                rs.getInt("bikes_allowed"));
    }

    /**
     * Finds all trips.
     * 
     * @return A list of all trips.
     */
    public List<Trip> getAll() {
        List<Trip> trips = new ArrayList<>();

        String select = "SELECT route_id, service_id, trip_id, trip_headsign, shape_id FROM filtered_trips";

        try (Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(select)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    trips.add(parseFindAllTrip(rs));
                }
            }
        } catch (Exception e) {
            Logger.getInstance().error(e.getMessage());
        }
        return trips;
    }

    /**
     * Parses a ResultSet object to a Trip object.
     * 
     * @param rs The ResultSet object.
     * @return The Trip object.
     */
    private Trip parseFindAllTrip(ResultSet rs) {
        try {
            return new Trip(
                    rs.getString("route_id"),
                    rs.getString("service_id"),
                    rs.getString("trip_id"),
                    rs.getString("trip_headsign"),
                    rs.getString("shape_id"));
        } catch (Exception e) {
            Logger.getInstance().error("TripRepository::parseFindAllTrip: " + e.getMessage());
        }
        return null;
    }

}
