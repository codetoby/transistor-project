package org.group14.utilities.gtfs;

import java.time.LocalDate;

public class DateHandler {

    private DateHandler() {}

    /**
     * Returns the 'date' in the format YYYYMMDD.
     */
    public static String getDate(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        String monthToString = month < 10 ? "0" + month : String.valueOf(month);
        String dayToString = day < 10 ? "0" + day : String.valueOf(day);
        return year + monthToString + dayToString;
    }
    
}
