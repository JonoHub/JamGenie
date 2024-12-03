package main.java.jamgenie.model;

public class User {
    private final String username;
    private final String password;
    public User(String username, String password){  // Yes, I know, constructor with a password. This project's deadline is in less than 2 weeks... The interface is the main point, not security.
        
        this.username = username;
        this.password = password;
        
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
