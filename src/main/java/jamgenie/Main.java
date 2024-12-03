package main.java.jamgenie;

import main.java.jamgenie.api.ApiException;
import main.java.jamgenie.controller.SearchController;

public class Main {

    public static void main(String[] args) throws ApiException {
        SearchController searchController = new SearchController();

        String albumName = "era vulgaris";
        String albumMethod = "album.search";

        String trackname = "Learn to Fly";
        String trackMethod = "track.search";

        //searchController.search(albumName, albumMethod);
        searchController.search(trackname, trackMethod);
    }
}