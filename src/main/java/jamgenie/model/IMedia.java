package jamgenie.model;

public interface IMedia {
    String getType();
    String getName();
    String getArtist();
    String getUrl();
    String getImageUrl();
    void like();
    Boolean isLiked();
}
