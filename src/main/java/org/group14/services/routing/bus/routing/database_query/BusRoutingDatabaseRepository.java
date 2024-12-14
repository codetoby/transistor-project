package org.group14.services.routing.bus.routing.database_query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.group14.services.gtfs.stops.Stop;
import org.group14.services.routing.bus.routing.database_query.model.PossibleTrip;
import org.group14.utilities.Logger;

public class BusRoutingDatabaseRepository implements IBusRoutingDatabaseRepository {

    private final DataSource datasource;

    public BusRoutingDatabaseRepository(DataSource dataSource) {
        this.datasource = dataSource;
    }

    public List<PossibleTrip> findPossibleTrips(Stop originStop, Stop destStop, String time, String date) {
        return findPossibleTrips(originStop, destStop, time, date, 10);
    }

    public List<PossibleTrip> findPossibleTrips(Stop originStop, Stop destStop, String time, String date, int limit) {
        List<PossibleTrip> possibleTrips = new LinkedList<>();
        String select = """
                SELECT
                  start.trip_id as trip_id,
                  start.stop_id as start_stop_id,
                  start.departure_time as departure_time,
                  end.stop_id as end_stop_id,
                  end.arrival_time as arrival_time,
                  SUBTIME(
                    end.arrival_time,
                    start.departure_time
                  ) as total_time,
                    (end.shape_dist_traveled - start.shape_dist_traveled) as total_distance,
                  dates.service_id
                FROM
                  stop_times as start
                  INNER JOIN stop_times as
                        end ON start.trip_id = end.trip_id
                    INNER JOIN trips ON start.trip_id = trips.trip_id
                    INNER JOIN calendar_dates as dates ON trips.service_id = dates.service_id
                WHERE
                    dates.date = ?
                    AND start.stop_id = ?
                    AND end.stop_id = ?
                    AND start.departure_time >= ?
                    AND start.departure_time < end.arrival_time
                ORDER BY
                    end.arrival_time
                LIMIT
                  ?;
                      """;

        try (Connection conn = this.datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(select)) {
            stmt.setString(1, date);
            stmt.setString(2, originStop.getStopId());
            stmt.setString(3, destStop.getStopId());
            stmt.setString(4, time);
            stmt.setInt(5, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                possibleTrips.add(parsePossibleTrip(rs));
            }

        } catch (Exception e) {
            Logger.getInstance().error("PossibleTripRepository::findPossibleTrips: " + e.getMessage());
        }

        return possibleTrips;
    }

    private PossibleTrip parsePossibleTrip(ResultSet rs) throws Exception {
        return new PossibleTrip(
                rs.getString("trip_id"),
                rs.getString("start_stop_id"),
                rs.getString("departure_time"),
                rs.getString("end_stop_id"),
                rs.getString("arrival_time"),
                rs.getString("total_time"),
                rs.getString("total_distance"));
    }
}
