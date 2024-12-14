package org.group14.services.zipcode.csv_file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.group14.domain.exceptions.zipcode.ZipCodeNotFound;
import org.group14.domain.models.Coordinate;
import org.group14.domain.models.ZipCode;
import org.group14.services.zipcode.IZipCodeService;
import org.group14.utilities.Logger;


public class CsvZipCodeService implements IZipCodeService {

    private final String FILE_PATH;
    private static final Logger logger = Logger.getInstance();
    private static final Map<String, Coordinate> zipCodeMap = new HashMap<>();

    public CsvZipCodeService(String filePath) {
        this.FILE_PATH = filePath;
    }

    /**
     * Get the coordinates of a zip code.
     * @param zipCode ZipCode
     * @return ZipCode
     */
    @Override
    public ZipCode getCoordiantesFromZipCode(String code) throws ZipCodeNotFound {
        if (zipCodeMap.isEmpty()) {
            readCSV();
        }
        Coordinate coordinate = zipCodeMap.get(code);

        if (coordinate == null) {
            throw new ZipCodeNotFound(code);
        }
        return new ZipCode(code, coordinate);
    }

    /**
     * Read the CSV file.
     */
    private void readCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH, StandardCharsets.UTF_8))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                addToZipCodeMap(line);
            }
        } catch (FileNotFoundException e) {
            logger.error("CSV - File not found. Please check the file path: " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Add a line from the CSV to the map.
     * @param csvLine
     */
    private void addToZipCodeMap(String csvLine) {
        String[] values = csvLine.split(";");
        String zip = values[0];
        String lat = values[1].replace(",", ".");
        String lon = values[2].replace(",", ".");
        double latDouble = Double.parseDouble(lat);
        double lonDouble = Double.parseDouble(lon);
        Coordinate coord = new Coordinate(latDouble, lonDouble);
        addToZipCodeMap(zip, coord);
    }

    /**
     * Add a zip code and coordinate to the map.
     * @param zipCode ZipCode
     * @param coord Coordinate
     */
    private static void addToZipCodeMap(String code, Coordinate coordinate) {
        zipCodeMap.put(code, coordinate);
    }

}
