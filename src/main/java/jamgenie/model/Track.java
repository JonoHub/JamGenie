package main.java.jamgenie.model;

public class Track {
    private final String name;
    private final String artist;
    private final String url;

    public Track(String name, String artist, String url) {
        this.name = name;
        this.artist = artist;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return name + " by " + artist + " (" + url + ")";
    }
}
