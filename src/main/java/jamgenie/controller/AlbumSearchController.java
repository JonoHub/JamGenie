package main.java.jamgenie.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.jamgenie.api.ApiException;
import main.java.jamgenie.api.LastFMApiHandler;
import main.java.jamgenie.api.LastFMApiParser;
import main.java.jamgenie.model.Album;

public class AlbumSearchController {
    private final LastFMApiHandler apiHandler;
    private final LastFMApiParser apiParser;

    public  AlbumSearchController() {
        this.apiHandler = new LastFMApiHandler();
        this.apiParser = new LastFMApiParser();
    }

    public void getAlbumSearch(String albumName) throws ApiException {
        String method = "album.search";
        Map<String, String> params = new HashMap<>();

        params.put("method", method);
        params.put("album", albumName);

        String response = apiHandler.sendRequest(params);
        System.out.println(response);
        List<Album> albums = apiParser.parseAlbums(response);
        for (Album album : albums) {
            album.toString();
        }
    }
}
