package jamgenie.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jamgenie.api.ApiException;
import jamgenie.model.IMedia;
import jamgenie.model.User;

public class SceneController {

    private SearchController searchController = new SearchController();

    private Boolean trackMode = true;

    private User user;

    private int listLimit = 5;

    @FXML
    private TextField artistPrompt;

    @FXML
    private Button albumButton;

    @FXML
    private VBox resultsVBox;

    @FXML
    private TextField searchPrompt;

    @FXML
    private Button trackButton;

    @FXML
    private ImageView profileIcon;

    @FXML
    void Search(ActionEvent event) throws ApiException {
        String nameSearch = searchPrompt.getText();
        String artistSearch = artistPrompt.getText();
        List<IMedia> elements = new ArrayList<>();
        
        // fetch elements: albums, tracks etc
        if (trackMode) {
            elements = searchController.search(nameSearch, "track.search", artistSearch);
        } else if (!trackMode) {
            elements = searchController.search(nameSearch, "album.search", artistSearch);
        }

        // Clear previous results
        resultsVBox.getChildren().clear();

        // Display new results
        for (int i = 0; i < listLimit; i++) {
            IMedia element = elements.get(i);
            VBox resultItem = new VBox(5); // VBox to hold name, artist, and image
            resultItem.setStyle("-fx-padding: 10px; -fx-border-color: gray; -fx-border-width: 1;");

            // Song name and artist
            Label nameLabel = new Label("Song: " + element.getName());
            Label artistLabel = new Label("Artist: " + element.getArtist());

            // Image
            ImageView imageView = new ImageView();
            if (element.getImageUrl() != null && !element.getImageUrl().isEmpty()) {
                Image image = new Image(element.getImageUrl());
                imageView.setImage(image);
                imageView.setFitWidth(100); // adjust image size
                imageView.setFitHeight(100);
            }

            // Create "Like" button
            Button likeButton = new Button("Like");
            likeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            likeButton.setOnAction(e -> handleLikeButtonClick(element, likeButton)); // handle like

            // Add components to result item
            resultItem.getChildren().addAll(nameLabel, artistLabel, imageView, likeButton);

            // Add result item to VBox
            resultsVBox.getChildren().add(resultItem);
        }
    }

    public void handleLikeButtonClick(IMedia element, Button likeButton) {
        if(!element.isLiked()) {
            user.addFavourites(element);
            likeButton.setText("Dislike");
            element.like();
        } else {
            user.removeFavourites(element);
            likeButton.setText("Like");
            element.like();
        }
        System.out.println(user.getFavourites());
    }

    @FXML
    void albumMode(ActionEvent event) {
        searchPrompt.setPromptText("Search for an album");
        albumButton.setDisable(true);
        trackButton.setDisable(false);
        trackMode = false;
    }

    @FXML
    void trackMode(ActionEvent event) {
        searchPrompt.setPromptText("Search for a track");
        trackButton.setDisable(true);
        albumButton.setDisable(false);
        trackMode = true;
    }

    @FXML
    void openProfile(MouseEvent event) {
        try {
            // Load the ProfileScene FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jamgenie/ProfileScene.fxml"));
            Parent profileRoot = loader.load();

            // Get the ProfileController
            ProfileController profileController = loader.getController();

            // Pass the current user to the profile controller
            profileController.setUser(user);

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene profileScene = new Scene(profileRoot);
            stage.setScene(profileScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUser(User user) {
        System.out.println("USER :" + user.getUsername());
        this.user = user;
    }
}

