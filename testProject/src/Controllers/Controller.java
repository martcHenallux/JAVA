package Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
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

    public Controller(){
        stage = new Stage();
    }

    @FXML
    public void startButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaces/Admin.fxml"));
        root = loader.load(); 
        scene = new Scene(root);  
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();  
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void initialize() throws IOException{
        MovementThread thread = new MovementThread(startingButton, logo);
        thread.start();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
   }

}
