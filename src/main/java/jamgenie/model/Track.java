package main.java.jamgenie.model;

/**
 * Class for a Track. A track is a single song
 */
public class Track implements IMedia {
    private final String name;
    private final String artist;
    private final String url;
    private final String imageUrl;

    public Track(String name, String artist, String url, String imageUrl) {
        this.name = name;
        this.artist = artist;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
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
