package jamgenie.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jamgenie.api.ApiException;
import jamgenie.api.LastFMApiHandler;
import jamgenie.api.LastFMApiParser;
import jamgenie.model.IMedia;

public class SearchController {
    private final LastFMApiHandler apiHandler;
    private final LastFMApiParser apiParser;

    public  SearchController() {
        this.apiHandler = new LastFMApiHandler();
        this.apiParser = new LastFMApiParser();
    }

    public List<IMedia> search(String name, String method, String artist) throws ApiException {
        List<IMedia> resultsList;
        switch (method) {
            case "album.search" : {
                resultsList = searchAlbum(name);
                return resultsList;
            }
            case "track.search" : {
                resultsList = searchTrack(name, method);
                return resultsList;
            }
            case "track.getsimilar" : {
                resultsList = searchSimilarTrack(name, method, artist);
                return resultsList;
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
    private List<IMedia> searchAlbum(String albumName) throws ApiException {
        albumName = fixName(albumName);
        //System.out.println(albumName);
        String method = "album.search";
        Map<String, String> params = new HashMap<>();

        params.put("method", method);
        params.put("album", albumName);

        String response = apiHandler.sendRequest(params);
        //System.out.println(response);
        List<IMedia> albums = apiParser.apiParser(response, method);
        for (int i = 0; i < 5; i++) {
            IMedia album = albums.get(i);
            System.out.println((i+1) + " : " + album.toString());
        }
        return albums;
    }

    /**
     * Controller for the album.search method. Called when the user searches for an albumName.
     * 
     * @param albumName
     * @throws ApiException
     */
    private List<IMedia> searchTrack(String trackName, String method) throws ApiException {
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
        return tracks;
    }

    /**
     * Controller for the track.similar method. Called when the user searches for similar tracks to the one given.
     * 
     * @param trackName
     * @throws ApiException
     */
    private List<IMedia> searchSimilarTrack(String trackName, String method, String artist) throws ApiException {
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
        for (int i = 0; i < 10; i++) {
            IMedia track = tracks.get(i);
            System.out.println((i+1) + " : " + track.toString());
        }
        return tracks;
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
