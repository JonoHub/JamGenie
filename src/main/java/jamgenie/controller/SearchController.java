package main.java.jamgenie.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.jamgenie.api.ApiException;
import main.java.jamgenie.api.LastFMApiHandler;
import main.java.jamgenie.api.LastFMApiParser;
import main.java.jamgenie.model.IMedia;

public class SearchController {
    private final LastFMApiHandler apiHandler;
    private final LastFMApiParser apiParser;

    public  SearchController() {
        this.apiHandler = new LastFMApiHandler();
        this.apiParser = new LastFMApiParser();
    }

    public void search(String name, String method, String artist) throws ApiException {
        switch (method) {
            case "album.search" : {
                searchAlbum(name);
                break;
            }
            case "track.search" : {
                searchTrack(name, method);
                break;
            }
            case "track.getsimilar" : {
                searchSimilarTrack(name, method, artist);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unsupported method: " + method);
            }
        }
    }
    /**
     * Controller for the album.search method. Called when the user searches for an albumName.
     * 
     * @param albumName
     * @throws ApiException
     */
    private void searchAlbum(String albumName) throws ApiException {
        albumName = fixName(albumName);
        //System.out.println(albumName);
        String method = "album.search";
        Map<String, String> params = new HashMap<>();

        params.put("method", method);
        params.put("album", albumName);

        String response = apiHandler.sendRequest(params);
        //System.out.println(response);
        List<IMedia> albums = apiParser.apiParser(response, method);
        int i = 0;
        for (IMedia album : albums) {
            System.out.println(i + " : " + album.toString());
            i++;
            if (i >= 11) {
                break;
            }
        }
    }

    /**
     * Controller for the album.search method. Called when the user searches for an albumName.
     * 
     * @param albumName
     * @throws ApiException
     */
    private void searchTrack(String trackName, String method) throws ApiException {
        trackName = fixName(trackName);
        //System.out.println(trackName);
        // String method = "track.search";
        Map<String, String> params = new HashMap<>();

        params.put("method", method);
        params.put("track", trackName);

        String response = apiHandler.sendRequest(params);
        //System.out.println(response);
        List<IMedia> tracks = apiParser.apiParser(response, method);
        System.out.println(tracks.size());
        //System.out.println("================================================================================================================");
        int i = 0;
        for (IMedia track : tracks) {
            if (i < 11) {
            System.out.println(i + " : " + track.toString());
            }
            i++;
        }
    }

    /**
     * Controller for the album.search method. Called when the user searches for an albumName.
     * 
     * @param albumName
     * @throws ApiException
     */
    private void searchSimilarTrack(String trackName, String method, String artist) throws ApiException {
        trackName = fixName(trackName);
        artist = fixName(artist);
        // System.out.println(trackName);
        // String method = "track.search";
        Map<String, String> params = new HashMap<>();

        params.put("method", method);
        params.put("artist", artist);
        params.put("track", trackName);

        String response = apiHandler.sendRequest(params);
        // System.out.println(response);
        List<IMedia> tracks = apiParser.apiParser(response, method);
        // System.out.println(tracks.size());
        //System.out.println("================================================================================================================");
        int i = 0;
        for (IMedia track : tracks) {
            if (i < 11) {
            System.out.println(i + " : " + track.toString());
            }
            i++;
        }
    }


    /**
     * Replaces all empty spaces (" ") in a String with '+'
     * 
     * @param albumName
     * @return
     */
    private String fixName(String albumName) {
        if (albumName == null){
            return null;
        }
        return albumName.replace(" ","+");
    }
}
