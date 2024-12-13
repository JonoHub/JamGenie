package jamgenie.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jamgenie.api.ApiException;
import jamgenie.model.IMedia;
import jamgenie.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProfileController {

    @FXML
    private Text favouriteLabel;

    @FXML
    private ToggleButton recommendedButton;

    @FXML
    private VBox resultsVBox;

    @FXML
    private Text usernameText;

    private User user; // Store the passed User

    private Boolean recommendedMode = false;

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
            if(!element.isLiked()) {
                user.addFavourites(element);
                likeButton.setText("Dislike");
                element.like();
            } else {
                user.removeFavourites(element);
                likeButton.setText("Like");
                element.like();
                resultsVBox.getChildren().remove(resultItem);
            }
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

   @FXML
    void pressRecommended(ActionEvent event) throws ApiException {
        if (!recommendedMode) {
            recommendedMode = true;

            // Check if the user has already given consent
            if (!user.doesUserConsent()) {
                // Keep asking for permission until the user consents
                while (!user.doesUserConsent()) {
                    boolean permission = showConsentDialog();
                    if (permission) {
                        user.setUserConsent(true); // Store user's decision
                    } else {
                        // If the user denies, exit recommendation mode
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Permission Denied");
                        alert.setHeaderText(null);
                        alert.setContentText("Recommendations require your consent to access favourites.");
                        alert.showAndWait();

                        recommendedMode = false;
                        favouriteLabel.setText("Favourites");
                        loadFavourites();
                        return;
                    }
                }
            }
            // If user consents, load recommendations
            loadRecommended();
        } else {
            // If exiting recommendation mode
            recommendedMode = false;
            favouriteLabel.setText("Favourites");
            loadFavourites();
        }
    }

    private boolean showConsentDialog() {
        // Create an alert dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Permission Required");
        alert.setHeaderText("Recommendation Permission");
        alert.setContentText("Do you allow the app to use your favourites to generate recommendations?");

        // Show the dialog and wait for the user's response
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }


    private void loadRecommended() throws ApiException {
        favouriteLabel.setText("AI Recommended");
        resultsVBox.getChildren().clear(); // Clear existing content
    
        List<IMedia> recommended = user.getRecommended();
        for (IMedia element : recommended) {
            VBox resultItem = new VBox(5); // VBox to hold name, artist, image, and source favorite
            resultItem.setStyle("-fx-padding: 10px; -fx-border-color: gray; -fx-border-width: 1;");
    
            // Song name and artist
            Label nameLabel = new Label("Song: " + element.getName());
            Label artistLabel = new Label("Artist: " + element.getArtist());
    
            // Display the favorite track/album that this recommendation is based on
            Label sourceLabel = new Label("Based on favorite: " + element.getBasedOn());
    
            // Image
            ImageView imageView = new ImageView();
            if (element.getImageUrl() != null && !element.getImageUrl().isEmpty()) {
                Image image = new Image(element.getImageUrl());
                imageView.setImage(image);
                imageView.setFitWidth(100); // Adjust image size
                imageView.setFitHeight(100);
            }
    
            // Create "Like" button
            Button likeButton = new Button("Like");
            likeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            likeButton.setOnAction(e -> handleLikeButtonClick(element, likeButton, resultItem)); // handle like
    
            // Add components to result item
            resultItem.getChildren().addAll(nameLabel, artistLabel, sourceLabel, imageView, likeButton);
    
            // Add result item to VBox
            resultsVBox.getChildren().add(resultItem);
        }
    }
}
