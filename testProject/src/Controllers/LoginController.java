package Controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button backButton;

    @FXML
    private Button confirmLoginDataButton;

    @FXML
    private TextField nickname;

    @FXML
    private PasswordField password;

    @FXML
    private Button registerButton;

    @FXML
    public void registerButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();   
        Scene scene = new Scene(root);  
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        // Stage mainWindow = (Stage) tfTitle.getScene().getWindow();$
    }

    @FXML
    public void backButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("RegisterLogin.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();   
        Scene scene = new Scene(root);  
        stage.setTitle("RegisterLogin");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        // Stage mainWindow = (Stage) tfTitle.getScene().getWindow();$
    }

    

}
