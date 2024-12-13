package jamgenie.model;

import java.util.*;

import jamgenie.api.ApiException;
import jamgenie.controller.SearchController;

public class User {
    private String username;
    private List<IMedia> favourites;
    private List<IMedia> recommended = new ArrayList<>();
    private Set<String> processedFavourites = new HashSet<>(); // Track processed favorites
    private Set<String> favouriteNames = new HashSet<>(); // Track favorite names for quick checks
    private Set<String> recommendedNames = new HashSet<>();
    private Boolean userConsent = null;

    private SearchController searchController = new SearchController();
    private int recommendedPerElement = 5;

    public User(String username) {
        this.username = username;
        this.favourites = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public List<IMedia> getFavourites() {
        return favourites;
    }

    public void removeFavourites(IMedia element) {
        favourites.remove(element);
        favouriteNames.remove(element.getName()); // Update tracked names
        processedFavourites.remove(element.getName()); // Allow re-processing if re-added
    }

    public void addFavourites(IMedia element) {
        favourites.add(element);
        favouriteNames.add(element.getName()); // Add to tracked names
    }

    public List<IMedia> getRecommended() throws ApiException {
        searchRecommended();
        return recommended;
    }

    public boolean doesUserConsent() {
        return Boolean.TRUE.equals(userConsent); // Return true if consent is granted
    }

    public void setUserConsent(boolean consent) {
        this.userConsent = consent; // Update consent
    }

    private void searchRecommended() throws ApiException {
        //recommended.clear(); // Clear previous recommendations
        for (IMedia element : favourites) {
            if (!processedFavourites.contains(element.getName())) {
                List<IMedia> similarList;
                if (element.getType().equalsIgnoreCase("album")) {
                    similarList = searchController.search(element.getName(), "album.getsimilar", element.getArtist());
                } else {
                    similarList = searchController.search(element.getName(), "track.getsimilar", element.getArtist());
                }
    
                for (IMedia similar : similarList.subList(0, Math.min(recommendedPerElement, similarList.size()))) {
                    if (!favouriteNames.contains(similar.getName()) && !recommendedNames.contains(similar.getName())) {
                        similar.setBasedOn(element.getName()); // Set the favorite this recommendation is based on
                        recommended.add(similar);
                        recommendedNames.add(similar.getName());
                    }
                }
                processedFavourites.add(element.getName());
            }
        }
        Collections.shuffle(recommended);
    }
    
    
}
