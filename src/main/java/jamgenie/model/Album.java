package main.java.jamgenie.model;

public class Album implements IMedia{
    private String name;
    private String artist;
    private String url;
    private String imageUrl;

    public Album(String name, String artist, String url, String imageUrl) {
        this.name = name;
        this.artist = artist;
        this.url = url;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return "Album{name='" + name + "', artist='" + artist + "', imageUrl='" + imageUrl + "'}";
    }
}