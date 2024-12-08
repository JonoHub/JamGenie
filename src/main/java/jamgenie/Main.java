package main.java.jamgenie;

import main.java.jamgenie.api.ApiException;
import main.java.jamgenie.controller.SearchController;

public class Main {

    public static void main(String[] args) throws ApiException {
        SearchController searchController = new SearchController();

        // String albumName = "era vulgaris";
        // String albumMethod = "album.search";

        // String trackName = "Learn to Fly";
        // String trackMethod = "track.search";

        String trackName = "Adore You";
        String trackMethod = "track.getsimilar";
        String artistName = "Harry Styles";

        // searchController.search(albumName, albumMethod);
        // System.out.println("----------------------------");
        // searchController.search(trackName, trackMethod);
        searchController.search(trackName, trackMethod, artistName);
    }
}