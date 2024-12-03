package main.java.jamgenie.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import main.java.jamgenie.model.Album;
import main.java.jamgenie.model.IMedia;
import main.java.jamgenie.model.Track;

import java.util.ArrayList;
import java.util.List;

public class LastFMApiParser {


    public List<IMedia> apiParser(String jsonResponse, String method) {
        String[] parts = method.split("\\.");
        String type = parts[0];

        switch (type) {
            case "album" : 
                return new ArrayList<>(parseAlbums(jsonResponse));
                
            case "track" : 
                return new ArrayList<>(parseTracks(jsonResponse));

            default:
            throw new IllegalArgumentException("Unknown method type: " + type);
        }
    }
    /**
     * Parses the JSON response for album information and returns a list of Album objects.
     * This would be the products of a album.search request.
     *
     * @param jsonResponse JSON response as a String.
     * @return A list of Album objects.
     */
    public List<Album> parseAlbums(String jsonResponse) {
        List<Album> albums = new ArrayList<>();
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

        // Check if the "albums" object and the "album" array are present
        if (jsonObject.has("results")) {
            JsonObject resultsObject = jsonObject.getAsJsonObject("results");
            if( resultsObject.has("albummatches")) {
                JsonObject albumMatchesObject = resultsObject.getAsJsonObject("albummatches");
                if (albumMatchesObject.has("album")) {
                    JsonArray albumArray = albumMatchesObject.getAsJsonArray("album");

                    // create album objects, iterating through array.
                    for (JsonElement albumElement : albumArray) {
                        JsonObject albumObject = albumElement.getAsJsonObject();

                        String name = albumObject.get("name").getAsString();
                        String artist = albumObject.get("artist").getAsString();
                        String url = albumObject.get("url").getAsString();
        
                        // extract image URL 
                        String imageUrl = extractImageUrl(albumObject);
        
                        // create an Album object and add it to the list
                        albums.add(new Album(name, artist, url, imageUrl));
                    }
                }
            }
        }
        return albums;
    }

    public List<Track> parseTracks(String jsonResponse) {
        List<Track> tracks = new ArrayList<>();
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

        if (jsonObject.has("results")) {
            JsonObject resultsObject = jsonObject.getAsJsonObject("results");
            if (resultsObject.has("trackmatches")) {
                JsonObject trackmatchesObject = resultsObject.getAsJsonObject("trackmatches");
                if (trackmatchesObject.has("track")) {
                    JsonArray trackArray = trackmatchesObject.getAsJsonArray("track");
                    

                    for (JsonElement trackElement : trackArray) {
                            JsonObject trackObject = trackElement.getAsJsonObject();

                            String name = trackObject.get("name").getAsString();
                            String artist = trackObject.get("artist").getAsString();
                            String url = trackObject.get("url").getAsString();

                            String imageUrl = extractImageUrl(trackObject);

                            tracks.add(new Track(name, artist, url, imageUrl));

                    }
                }
            }
        }

        return tracks;
    }

    /**
     * Extracts the image URL for the "medium" size image from the album object.
     *
     * @param albumObject JSON object containing the album information.
     * @return The image URL as a String.
     */
    private String extractImageUrl(JsonObject albumObject) {
        JsonArray imageArray = albumObject.getAsJsonArray("image");
        if (imageArray != null && imageArray.size() > 0) {
            for (JsonElement imageElement : imageArray) {
                JsonObject imageObject = imageElement.getAsJsonObject();
                // Check for medium size image
                if (imageObject.has("size") && imageObject.get("size").getAsString().equals("extralarge")) {
                    return imageObject.get("#text").getAsString();
                }
            }
        }
        return ""; // Return empty string if no image found
    }
}