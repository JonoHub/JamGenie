package jamgenie;

import jamgenie.controller.SceneController;
import jamgenie.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            User user = new User("jono");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
            Parent root = loader.load();

            SceneController sceneController = loader.getController();

            sceneController.setUser(user);

            // Set up the Scene and Stage
            Scene scene = new Scene(root);
            primaryStage.setTitle("JamGenie");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
