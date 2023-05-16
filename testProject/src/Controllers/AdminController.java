package Controllers;
//import Manager.*;
import Model.Country;
import Model.Locality;
import Model.Address;

import java.util.ArrayList;    
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;




public class AdminController {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;

    @FXML
    private Button countryButton;

    @FXML
    private Button customerButton;

    @FXML
    private Button createButton;

    @FXML
    private Button readButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button submitButton;


    public void buttonClicked(Button button){
    }

    @FXML
    private void createButtonOnClick(){
        createButton.setDisable(true);
        readButton.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }
    @FXML
    private void readButtonOnClick(){
        createButton.setDisable(false);
        readButton.setDisable(true);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }
    @FXML
    private void updateButtonOnClick(){
        createButton.setDisable(false);
        readButton.setDisable(false);
        updateButton.setDisable(true);
        deleteButton.setDisable(false);
    }
    @FXML
    private void deleteButtonOnClick(){
        createButton.setDisable(false);
        readButton.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(true);
    }

    @FXML
    private void submitButtonOnClick(){
        if(firstName.getText() == null){
            //throw exception
            
        }
    }

    

}

    // private CountryManager countryManager;
    // private LocalityManager localityManager;
    // private AddressDataManager addressManager;
    

    // public ArrayList<Country> getAllCountries(){
    //     return countryManager.getAllCountries();
    // }

    // public ArrayList<Locality> getAllLocalities(){
    //     return localityManager.getAllLocalities();
    // }

    // public ArrayList<Address> getAllAddresses(){
    //     return addressManager.getAllAddresses();
    // }
//}
