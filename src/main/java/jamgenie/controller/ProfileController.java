package jamgenie.controller;

import java.io.IOException;
import java.util.List;
import jamgenie.model.IMedia;
import jamgenie.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProfileController {

    @FXML
    private VBox resultsVBox;

    @FXML
    private Text usernameText;

    private User user; // Store the passed User

    // Method to set the current user
    public void setUser(User user) {
        this.user = user;
        usernameText.setText(user.getUsername()); // Update UI with username
        loadFavourites(); // Load the user's favourites
    }

    private void loadFavourites() {
        resultsVBox.getChildren().clear(); // Clear existing content

        List<IMedia> favourites = user.getFavourites();
        for (IMedia element : favourites) {
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

            // Create "Like" button
            Button likeButton = new Button("Remove");
            likeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            likeButton.setOnAction(e -> handleLikeButtonClick(element, likeButton, resultItem)); // handle like
            
            // Add components to result item
            resultItem.getChildren().addAll(nameLabel, artistLabel, imageView, likeButton);

            // Add result item to VBox
            resultsVBox.getChildren().add(resultItem);
        }
    }

    private void handleLikeButtonClick(IMedia element, Button likeButton, VBox resultItem) {
            user.removeFavourites(element);
            element.like();
            resultsVBox.getChildren().remove(resultItem);
            System.out.println(user.getFavourites());
    }

    @FXML
    void openHome(MouseEvent event) {
        try {
            // Load the MainScene FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jamgenie/MainScene.fxml"));
            Parent root = loader.load();
            // Get the current stage and set the new scene
            SceneController sceneController = loader.getController();

            sceneController.setUser(user);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene profileScene = new Scene(root);
            stage.setScene(profileScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
