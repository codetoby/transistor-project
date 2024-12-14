package org.group14.services.zipcode.api;

import org.group14.domain.models.Coordinate;
import org.group14.domain.models.ZipCode;
import org.group14.services.zipcode.IZipCodeService;
import org.group14.services.zipcode.api.requests.RequestManager;
import org.group14.services.zipcode.api.requests.RequestPeriod;
import org.group14.domain.exceptions.zipcode.ZipCodeNotFound;
import org.group14.utilities.Logger;

import java.net.*;
import java.io.*;

/**
 * This class fetches data from an API.
 */
public class ApiZipCodeService implements IZipCodeService {

    private static final String POSTCODE_URL = "https://computerscience.dacs.unimaas.nl/get_coordinates";
    private static final Logger logger = Logger.getInstance();

    /**
     * Check if a request can be made.
     * @return boolean
     */
    private static boolean canRequest() {
        long timeOff = RequestManager.getTimeUntilNextRequest();
        logger.log("Time until next request: " + RequestManager.millisToTime(timeOff) + "...");

        if (timeOff == 0) {
            return true;
        }
            

        if (timeOff <= RequestPeriod.FIVE_SECONDS.getPeriodInMillis()) {
            try {
                Thread.sleep(timeOff + 5000);
                return true;
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        logger.error("Request limit exceeded. Time until next request: " + RequestManager.millisToTime(timeOff));
        return false;
    }

    /**
     * Get the coordinates of a zip code.
     * @param zipCode ZipCode
     * @return Coordinate
     */
    public ZipCode getCoordiantesFromZipCode(String code) throws ZipCodeNotFound {
        if (!canRequest()) {
            return null;
        }

        logger.log("Calling API...");
        try {
            URI uri = new URI(POSTCODE_URL);
            URL url = uri.toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            String payload = "{\"postcode\": \"" + code + "\"}";
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(payload);
            writer.flush();
            writer.close();

            RequestManager.writeRequestToCSV();

            // check status
            int status = con.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                logger.error("aError while Fetching API: " + status);
                return null;
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String json = extractJson(reader);
            Coordinate coordinate = parseJson(json);
            return new ZipCode(code, coordinate);

        } catch (UnknownHostException e) {
            logger.error("Please use your VPN to access the API");
        } catch (URISyntaxException e) {
            logger.error("Invalid URI");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid URL");
        } catch (IOException e) {
            logger.error(e.getMessage());
        } 

        return null;
    }

    /**
     * Extract the JSON from the response.
     * @param reader BufferedReader
     * @return String
     * @throws IOException
     */
    private static String extractJson(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private static Coordinate parseJson(String json) {
        json = json.replaceAll("\\s+", "");
        json = json.replace("\\{", "");
        json = json.replace("}", "");
        json = json.replace(":", "");
        String[] parts = json.split("\"");
        return new Coordinate(Double.parseDouble(parts[3]), Double.parseDouble(parts[7]));
    }

}



