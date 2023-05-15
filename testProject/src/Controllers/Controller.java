package Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private ImageView logo;

    @FXML 
    private Button startingButton;

    @FXML private AnchorPane ap;

    public Controller(){
        stage = new Stage();
    }

    @FXML
    public void startButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("interfaces/RegisterLogin.fxml"));
        root = loader.load();
        Stage newStage = (Stage)((Node)event.getSource()).getScene().getWindow();   
        scene = new Scene(root);  
        newStage.setTitle("Hello World!");
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.show();
    }

    @FXML
    public void initialize() throws IOException{
        
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("interfaces/Scene.fxml"));
        // Parent root = loader.load();

        // stage.setTitle("null");
        // stage.show();
        startingButton.setText(Double.toString(startingButton.getWidth()));
        MovementThread thread = new MovementThread(startingButton, logo, stage);
        thread.start();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
   }

}
