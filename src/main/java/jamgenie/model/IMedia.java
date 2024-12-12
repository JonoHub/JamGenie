package jamgenie.model;

public interface IMedia {
    String getName();
    String getArtist();
    String getUrl();
    String getImageUrl();
    void like();
    Boolean isLiked();
}
