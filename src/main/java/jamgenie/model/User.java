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

    private void searchRecommended() throws ApiException {
        recommended.clear(); // Clear previous recommendations
        for (IMedia element : favourites) {
            // Skip already processed favorites
            if (!processedFavourites.contains(element.getName())) {
                List<IMedia> similarList;
    
                // Check if the element is an album or track and adjust the search method
                if (element.getType().equalsIgnoreCase("album")) { // Assuming `IMedia` has a `getType()` method
                    similarList = searchController.search(element.getName(), "album.setsimilar", element.getArtist());
                } else {
                    similarList = searchController.search(element.getName(), "track.getsimilar", element.getArtist());
                }
    
                // Add only up to `recommendedPerElement`, avoid duplicates, and skip if in favorites
                for (IMedia similar : similarList.subList(0, Math.min(recommendedPerElement, similarList.size()))) {
                    if (!favouriteNames.contains(similar.getName()) && !recommendedNames.contains(similar.getName())) {
                        recommended.add(similar);
                        recommendedNames.add(similar.getName()); // Track added recommendation
                    }
                }
    
                // Mark this favorite as processed
                processedFavourites.add(element.getName());
            }
        }
    
        Collections.shuffle(recommended);
    }
    
}
