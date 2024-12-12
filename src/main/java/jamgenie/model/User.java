package jamgenie.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    // private String password;
    private List<IMedia> favourites;

    public User(String username){  
        
        this.username = username;
        this.favourites = new ArrayList<>();
    }
    public String getUsername() {
        return username;
    }
    // public String getPassword() {
    //     return password;
    // }

    public List<IMedia> getFavourites() {
        return favourites;
    }
    public void removeFavourites(IMedia element) {
        favourites.remove(element);
    }
    public void addFavourites(IMedia element) {
        favourites.add(element);
    }
}
