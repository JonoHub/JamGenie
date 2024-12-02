package main.java.jamgenie.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;

public class LastFMApiHandler {
    private static final String API_BASE_URL = "http://ws.audioscrobbler.com/2.0/";
    private static final String API_KEY = "a51a10f5ad5015da252cad230eec7ad4"; 

    /**
     * Sends a GET request to the Last.fm API.
     * example request : http://ws.audioscrobbler.com/2.0/?method=getartisttopalbums&artist=cher&api_key=a51a10f5ad5015da252cad230eec7ad4&format=json
     *
     * @param params Query parameters as key-value pairs.
     * @return API response as a String.
     * @throws ApiException If the API request fails.
     */
    public String sendRequest(Map<String, String> params) throws ApiException {
        try {
            // build the query
            StringBuilder queryString = new StringBuilder("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                queryString.append(entry.getKey())
                           .append("=")
                           .append(entry.getValue())
                           .append("&");
            }
            queryString.append("api_key=").append(API_KEY).append("&format=json");
            //System.out.println(API_BASE_URL + queryString);
            // form the full URL
            URI uri = new URI(API_BASE_URL + queryString);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // check for successful response
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new ApiException("Failed to fetch data. Response Code: " + responseCode);
            }

            // read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();
        } catch (Exception e) {
            throw new ApiException("Error while calling Last.fm API: " + e.getMessage(), e);
        }
    }
}