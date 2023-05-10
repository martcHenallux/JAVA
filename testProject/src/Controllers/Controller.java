package Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Controller {
    private Stage stage;
    private Scene scene;

    @FXML
    public void startButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("RegisterLogin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();   
        scene = new Scene(root);  
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        // Stage mainWindow = (Stage) tfTitle.getScene().getWindow();$
    }

}
