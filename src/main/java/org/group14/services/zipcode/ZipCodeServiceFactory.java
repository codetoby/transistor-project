package org.group14.services.zipcode;

import org.group14.services.zipcode.api.ApiZipCodeService;
import org.group14.services.zipcode.csv_file.CsvZipCodeService;
import org.group14.services.zipcode.database.DatabaseZipCodeService;

public class ZipCodeServiceFactory {

    public static IZipCodeService createService(ZipCodeServiceEnums service) {
        return switch (service) {
            case DATABASE -> new DatabaseZipCodeService();
            case CSV -> new CsvZipCodeService("resources/zipcode_coordinate.csv");
            case API -> new ApiZipCodeService();
        };
    }
    
}
