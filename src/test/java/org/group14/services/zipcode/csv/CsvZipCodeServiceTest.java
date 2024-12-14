package org.group14.services.zipcode.csv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.group14.domain.exceptions.zipcode.ZipCodeNotFound;
import org.group14.domain.models.Coordinate;
import org.group14.domain.models.ZipCode;
import org.group14.services.zipcode.csv_file.CsvZipCodeService;

public class CsvZipCodeServiceTest {

    private static final String FILE_PATH = "src/test/resources/org/group14/zipcodes.csv";

    private CsvZipCodeService csvZipCodeService;


    private static final String VALID_ZIPCODE = "6211AL";
    private static final String ZIPCODE_NOT_FOUND = "6222AL";

    @BeforeEach
    public void setUp() {
        csvZipCodeService = new CsvZipCodeService(FILE_PATH);
    }

    @Test
    public void testValidZipCode() throws ZipCodeNotFound {
        ZipCode zipcode = new ZipCode(VALID_ZIPCODE, new Coordinate(50.8552328, 5.5692237193));
        assertEquals(zipcode, csvZipCodeService.getCoordiantesFromZipCode(VALID_ZIPCODE));
    }

    @Test
    public void testZipCodeNotFound() {
        assertThrows(ZipCodeNotFound.class, () -> csvZipCodeService.getCoordiantesFromZipCode(ZIPCODE_NOT_FOUND));
    }

    
}
