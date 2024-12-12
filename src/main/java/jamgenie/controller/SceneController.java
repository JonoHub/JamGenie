package jamgenie.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import jamgenie.api.ApiException;

public class SceneController {

    private SearchController searchController;

    @FXML
    private TextField artist;

    @FXML
    private TextField name;

    @FXML
    void Search(ActionEvent event) throws ApiException {

        searchController.search(name.getText() , "album.search", artist.getText());
    }

}

