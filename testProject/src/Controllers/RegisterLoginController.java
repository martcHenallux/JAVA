
package Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class RegisterLoginController {
    
    private static final String IDLE_REGISTER_BUTTON_STYLE = "-fx-background-color: efefef; -fx-border-width: 2; -fx-border-color: black; -fx-text-fill: black;";
    private static final String HOVERED_REGISTER_BUTTON_STYLE = "-fx-background-color: black; -fx-border-width: 2; -fx-border-color: black; -fx-text-fill: efefef;";
    
    private static final String IDLE_LOGIN_BUTTON_STYLE = "-fx-background-color: efefef; -fx-border-width: 2; -fx-border-color: black; -fx-text-fill: black;";
    private static final String HOVERED_LOGIN_BUTTON_STYLE = "-fx-background-color: black; -fx-border-width: 0 0 0 2; -fx-border-color: black; -fx-text-fill: efefef;";
    @FXML
    private Button registerButton;
    
    @FXML
    private Button loginButton;

    @FXML
    public void mouseEnteredRegister(MouseEvent e) {
        registerButton.setStyle(HOVERED_REGISTER_BUTTON_STYLE);
    }

    @FXML
    public void mouseExitedRegister(MouseEvent e) {
        registerButton.setStyle(IDLE_REGISTER_BUTTON_STYLE);
    }

    @FXML
    public void mouseEnteredLogin(MouseEvent e) {
        loginButton.setStyle(HOVERED_LOGIN_BUTTON_STYLE);
    }

    @FXML
    public void mouseExitedLogin(MouseEvent e) {
        loginButton.setStyle(IDLE_LOGIN_BUTTON_STYLE);
    }

    @FXML
    public void loginButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();   
        Scene scene = new Scene(root);  
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
    }

    @FXML
    public void registerButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();   
        Scene scene = new Scene(root);  
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
