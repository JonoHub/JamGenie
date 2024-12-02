package main.java.jamgenie;

import main.java.jamgenie.api.ApiException;
import main.java.jamgenie.controller.AlbumSearchController;

public class Main {

    public static void main(String[] args) throws ApiException {
        AlbumSearchController albumSearchController = new AlbumSearchController();

        String albumName = "hamilton";

        albumSearchController.getAlbumSearch(albumName);
    }
}