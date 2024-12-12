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

public class SceneController {

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
        String artistSearch = searchPrompt.getText();
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
                imageView.setFitWidth(100); // Adjust image size
                imageView.setFitHeight(100);
            }

            // Add components to result item
            resultItem.getChildren().addAll(nameLabel, artistLabel, imageView);

            // Add result item to VBox
            resultsVBox.getChildren().add(resultItem);
        }
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

