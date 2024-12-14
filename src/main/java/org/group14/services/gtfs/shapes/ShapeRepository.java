package org.group14.services.gtfs.shapes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.group14.domain.models.Coordinate;
import org.group14.utilities.Logger;
import org.springframework.stereotype.Repository;

/**
 * ShapeRepository class is responsible for handling the database operations for
 * the Shape class.
 */
@Repository
public class ShapeRepository implements IShapeRepository {

    private final DataSource dataSource;

    public ShapeRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Finds all shapes with the given shape_id.
     *
     * @param id The shape_id of the shapes.
     * @return A list of shapes with the given shape_id.
     */
    public List<Shape> getById(String id) {
        List<Shape> shapes = new LinkedList<>();
        String select = """
                SELECT
                    shape_id, shape_pt_sequence, shape_pt_lat, shape_pt_lon, shape_dist_traveled
                FROM
                    shapes
                WHERE
                    shape_id = ?
                ORDER BY
                    shape_pt_sequence
                """;

        try (Connection connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(select)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    shapes.add(parseShape(rs));
                }
            }
        } catch (Exception e) {
            Logger.getInstance().error("ShapeRepository::findById: " + e.getMessage());
        }

        return shapes;
    }

    /**
     * Finds the closest shape sequence to the given coordinate.
     * 
     * @param coordinate The coordinate to find the closest shape sequence to.
     * @param shapeId    The shape_id of the shapes.
     * @return The closest shape sequence to the given coordinate.
     */
    public Shape findClosestShapeSequence(Coordinate coordinate, String shapeId) {

        String select = """
                SELECT
                    *, calculate_distance(?, ?, shape_pt_lat, shape_pt_lon) as distance
                FROM
                    shapes
                WHERE
                    shape_id = ?
                ORDER BY
                    distance
                LIMIT 1
                """;

        try (Connection connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(select)) {
            pstmt.setDouble(1, coordinate.getLatitude());
            pstmt.setDouble(2, coordinate.getLongitude());
            pstmt.setString(3, shapeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return parseShape(rs);
                }
            }
        } catch (Exception e) {
            Logger.getInstance().error("ShapeRepository::findClosestShapeSequence: " + e.getMessage());
        }
        return null;
    }

    /**
     * Parses a ResultSet object into a Shape object.
     * 
     * @param rs The ResultSet object to be parsed.
     * @return The Shape object parsed from the ResultSet object.

     */
    private Shape parseShape(ResultSet rs) throws SQLException {
        return new Shape(
                rs.getString("shape_id"),
                rs.getString("shape_pt_lat"),
                rs.getString("shape_pt_lon"),
                rs.getInt("shape_pt_sequence"),
                rs.getString("shape_dist_traveled"));
    }

}
