package org.group14.services.zipcode.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.group14.domain.exceptions.zipcode.ZipCodeNotFound;
import org.group14.domain.models.Coordinate;
import org.group14.domain.models.ZipCode;
import org.group14.utilities.Logger;

public class DatabaseZipCodeRepository implements IZipCodeRepository{

    private final DataSource dataSource;

    public DatabaseZipCodeRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ZipCode findByCode(String code) throws ZipCodeNotFound {
        String select = "SELECT * FROM zipcodes WHERE zipcode_code = ?";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(select);) {

            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return parseZipCode(rs);
        } catch (Exception e) {
            Logger.getInstance().error("ZipCodeRepository::findByCode: " + e.getMessage());
        }
        throw new ZipCodeNotFound(code);
    }


    private ZipCode parseZipCode(ResultSet rs) {
        try {
            String lat = rs.getString("zipcode_lat");
            String lon = rs.getString("zipcode_lon");
            Coordinate coordinates = new Coordinate(Double.parseDouble(lat), Double.parseDouble(lon));
            return new ZipCode(
                    rs.getString("zipcode_code"),
                    coordinates);
        } catch (Exception e) {
            Logger.getInstance().error("ZipCodeRepository::parseZipCode " + e.getMessage());
        }
        return null;
    }
    
}
