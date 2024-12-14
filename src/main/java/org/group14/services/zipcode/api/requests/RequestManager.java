package org.group14.services.zipcode.api.requests;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.group14.utilities.Logger;

public class RequestManager {

    private static final String FILE_PATH = "resources\\request_history.csv";
    private static final Logger logger = Logger.getInstance();
    private static final String CURRENT_USER_IP = getLocalIp();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private RequestManager() {
        throw new IllegalStateException("RequestManager class");
    }

    /**
     * This method returns the local IP address.
     * @return The local IP address.
     */
    private static String getLocalIp() {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            logger.error(e.getMessage());
        }

        if (ip == null) {
            return null;
        }
        return ip.getHostAddress();
    }

    /**
     * This method returns the current time.
     * @return
     */
    private static synchronized String getCurrentTime() {
        return DATE_FORMAT.format(new Date());
    }

    /**
     * This method builds a log line.
     * @return
     */
    private static String buildLogLine() {
        return getCurrentTime() + ';' + CURRENT_USER_IP + '\n';
    }

    /**
     * This method writes a request to a CSV file.
     */
    public static void writeRequestToCSV() {
        String logLine = buildLogLine();
        try {
            Writer output = new BufferedWriter(new FileWriter(FILE_PATH, true));
            output.append(logLine);
            output.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * This method reads a request from a CSV file.
     * @return ArrayList<RequestRecord>
     */
    private static List<RequestRecord> readRequestFromCSV() {
        List<RequestRecord> requestRecords = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH, StandardCharsets.UTF_8))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                Date date = DATE_FORMAT.parse(values[0]);
                String ip = values[1];
                requestRecords.add(new RequestRecord(date, ip));
            }
        } catch (FileNotFoundException e) {
            logger.error("CSV - File not found");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }

        return requestRecords;
    }

    /**
     * This method returns the request history.
     * @param requestRecords
     * @return RequestHistory
     */
    private static RequestHistory getRequestHistory(List<RequestRecord> requestRecords) {
        Date currentTime = new Date();
        int fiveSeconds = 0;
        int oneMinute = 0;
        int oneHour = 0;
        int oneDay = 0;

        for (RequestRecord r : requestRecords) {
            if (RequestPeriod.FIVE_SECONDS.isShorterThan(r, currentTime)) fiveSeconds++;
            if (RequestPeriod.ONE_MINUTE.isShorterThan(r, currentTime)) oneMinute++;
            if (RequestPeriod.ONE_HOUR.isShorterThan(r, currentTime)) oneHour++;
            if (RequestPeriod.ONE_DAY.isShorterThan(r, currentTime)) oneDay++;
            //if (RequestPeriod.ONE_DAY.isLongerThan(r, currentTime)) requestRecords.remove(r);
        }

        return new RequestHistory(fiveSeconds, oneMinute, oneHour, oneDay);
    }

    /**
     * This method returns the time until the next request.
     * @param reqHist
     * @param reqRecords
     * @param currentTime
     * @param period
     * @return long
     */ 
    private static long getPeriodTimeOff(
            RequestHistory reqHist, List<RequestRecord> reqRecords, Date currentTime, RequestPeriod period
    ){
        if (!period.wasLimitExceeded(reqHist)) return 0;
        List<RequestRecord> periodRequests = reqRecords
                .stream()
                .filter(r -> currentTime.getTime() - r.date().getTime() < period.getPeriodInMillis())
                .collect(Collectors.toCollection(ArrayList::new));

        long timeUntilNextRequest = periodRequests.getFirst().date().getTime() + period.getPeriodInMillis();
        return timeUntilNextRequest - currentTime.getTime();
    }

    /**
     * This method converts milliseconds to time.
     * @param millis
     * @return String
     */
    public static String millisToTime(long millis) {
        return String.format("%02d:%02d:%02d",
                (millis / (1000 * 60 * 60)) % 24,
                (millis / (1000 * 60)) % 60,
                (millis / 1000) % 60);
    }

    /**
     * This method returns the time until the next request.
     * @return long
     */
    public static long getTimeUntilNextRequest() {
        List<RequestRecord> reqRecords = readRequestFromCSV();
        RequestHistory reqHist = getRequestHistory(reqRecords);
        reqRecords.sort((r1, r2) -> r1.date().compareTo(r2.date()));
        long maxTimeOff = 0;

        for (RequestPeriod period : RequestPeriod.values()) {
            long timeOff = getPeriodTimeOff(reqHist, reqRecords, new Date(), period);
            maxTimeOff = Math.max(maxTimeOff, timeOff);
        }

        return maxTimeOff;
    }

}
