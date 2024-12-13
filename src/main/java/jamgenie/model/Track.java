package jamgenie.model;

/**
 * Class for a Track. A track is a single song
 */
public class Track implements IMedia {
    private final String name;
    private final String artist;
    private final String url;
    private final String imageUrl;
    private Boolean isLiked;
    private String basedOn;

    public Track(String name, String artist, String url, String imageUrl) {
        this.name = name;
        this.artist = artist;
        this.url = url;
        this.imageUrl = imageUrl;
        this.isLiked = false;
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

    public Boolean isLiked() {
        return isLiked;
    }

    public void like() {
        isLiked = !isLiked;
    }
    public String getType(){
        return "track";
    }

    public void setBasedOn(String basedOn) {
        this.basedOn = basedOn;
    }

    public String getBasedOn() {
        return basedOn;
    }
    
    @Override
    public String toString() {
        return name + " by " + artist + " (" + imageUrl + ")";
    }
}
