package org.group14.utilities.gtfs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeHandler {

    private TimeHandler() {}

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static LocalDateTime parseTime(String time, LocalDate date) {
        if (time.startsWith("24:")) {
            time = "00:" + time.substring(3);
        } else if (time.startsWith("25:")) {
            time = "01:" + time.substring(3);
        }
        LocalTime localTime = LocalTime.parse(time, formatter);
        return LocalDateTime.of(date, localTime);
    }

    public static String getTime(LocalDateTime time) {
        // make sure there is a leading zero if the time is less than 10
        String hour = time.getHour() < 10 ? "0" + time.getHour() : String.valueOf(time.getHour());
        String minute = time.getMinute() < 10 ? "0" + time.getMinute() : String.valueOf(time.getMinute());
        String seconds = time.getSecond() < 10 ? "0" + time.getSecond() : String.valueOf(time.getSecond());
        return hour + ":" + minute + ":" + seconds;
    }

    public static String formatTimeToWalk(LocalDateTime time, String timeToWalk) {
        return time.plusHours(Integer.parseInt(timeToWalk.split(":")[0]))
                .plusMinutes(Integer.parseInt(timeToWalk.split(":")[1]))
                .plusSeconds(Integer.parseInt(timeToWalk.split(":")[2]))
                .format(formatter);
    }
    
}
