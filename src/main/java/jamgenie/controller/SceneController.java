package jamgenie.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

import jamgenie.api.ApiException;
import jamgenie.model.IMedia;
import jamgenie.model.User;

public class SceneController {

    User newUser = new User("janito");


    private SearchController searchController = new SearchController();

    private Boolean trackMode = true;

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
        for (IMedia element : elements) {
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

    private void handleLikeButtonClick(IMedia element, Button likeButton) {
        if(!element.isLiked()) {
            newUser.addFavourites(element);
            likeButton.setText("Dislike");
            element.like();
        } else {
            newUser.removeFavourites(element);
            likeButton.setText("Like");
            element.like();
        }
        System.out.println(newUser.getFavourites());
    }

    @FXML
    void albumMode(ActionEvent event) {
        searchPrompt.setPromptText("Search for an album");
        trackMode = false;
    }

    @FXML
    void trackMode(ActionEvent event) {
        searchPrompt.setPromptText("Search for a track");
        trackMode = true;
    }
}

