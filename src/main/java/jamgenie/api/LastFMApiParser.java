package main.java.jamgenie.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import main.java.jamgenie.model.Album;

import java.util.ArrayList;
import java.util.List;

public class LastFMApiParser {

    /**
     * Parses the JSON response for album information and returns a list of Album objects.
     * This would be the products of a album.search request.
     *
     * @param jsonResponse JSON response as a String.
     * @return A list of Album objects.
     */
    public List<Album> parseAlbums(String jsonResponse) {
        List<Album> albums = new ArrayList<>();
        JsonObject jsonObject = com.google.gson.JsonParser.parseString(jsonResponse).getAsJsonObject();

        // Check if the "albums" object and the "album" array are present
        if (jsonObject.has("results")) {
            JsonObject albumsObject = jsonObject.getAsJsonObject("albums");
            JsonArray albumArray = albumsObject.getAsJsonArray("album");

            // Iterate through each album and create Album objects
            for (JsonElement albumElement : albumArray) {
                JsonObject albumObject = albumElement.getAsJsonObject();

                // Extract album details
                String name = albumObject.get("name").getAsString();
                String artist = albumObject.get("artist").getAsString();
                String url = albumObject.get("url").getAsString();

                // Extract image URL (assume we want the "medium" size image)
                String imageUrl = extractImageUrl(albumObject);

                // Create an Album object and add it to the list
                albums.add(new Album(name, artist, url, imageUrl));
            }
        }
        return albums;
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
                if (imageObject.has("size") && imageObject.get("size").getAsString().equals("medium")) {
                    return imageObject.get("#text").getAsString();
                }
            }
        }
        return ""; // Return empty string if no image found
    }
}