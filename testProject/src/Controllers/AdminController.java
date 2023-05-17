package Controllers;
//import Manager.*;
import Model.Country;
import Model.Locality;
import Model.Address;
import Model.BusinessEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Objects;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import Business.AddressManager;
import Business.BusinessEntityManager;
import Business.CountryManager;
import Business.LocalityManager;
import Controllers.AdminException.errorNumber;
import javafx.scene.Node;


public class AdminController {
    @FXML
    private AnchorPane customerCreationPane;

    @FXML
    private AnchorPane updatePane;

    @FXML
    private FlowPane addressPane;

    @FXML
    private TextField lastName;

    @FXML
    private TextField firstName;

    @FXML
    private TextField email;

    @FXML
    private TextField updatedEmail;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;
    
    @FXML
    private CheckBox isCustomer;

    @FXML
    private CheckBox isSupplier;

    @FXML
    private CheckBox updateIsCustomer;

    @FXML
    private CheckBox updateIsSupplier;

    @FXML
    private CheckBox enterAddress; 
    
    @FXML
    private DatePicker birthdate;

    @FXML
    private TextField houseNumber;

    @FXML
    private TextField street;

    @FXML
    private TextField locality;

    @FXML
    private TextField postalCode;
    
    @FXML
    private ComboBox<String> countryChoice;

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

    @FXML
    private Button updateSubmitButton;

    @FXML
    private Button researchButton;

    @FXML
    private TableView<EntryFromTable> table;

    @FXML
    private TableColumn<EntryFromTable, String> column1;

    @FXML
    private TableColumn<EntryFromTable, String> column2;

    @FXML
    private TableColumn<EntryFromTable, String> column3;

    @FXML
    private TableColumn<EntryFromTable, String> column4;

    @FXML
    private TableColumn<EntryFromTable, String> column5;

    @FXML
    private TableColumn<EntryFromTable, String> column6;

    @FXML
    private TableColumn<EntryFromTable, String> column7;

    @FXML
    private TableColumn<EntryFromTable, String> column8;

    @FXML
    private TableColumn<EntryFromTable, String> column9;

    @FXML
    private TableColumn<EntryFromTable, String> column10;

    private EntryFromTable updateBusinessEntity;


    private void setTableItems(ObservableList<EntryFromTable> items) {
        column1.setCellValueFactory(new PropertyValueFactory<EntryFromTable, String>("column1"));
        column2.setCellValueFactory(new PropertyValueFactory<EntryFromTable, String>("column2"));
        column3.setCellValueFactory(new PropertyValueFactory<EntryFromTable, String>("column3"));
        column4.setCellValueFactory(new PropertyValueFactory<EntryFromTable, String>("column4"));
        column5.setCellValueFactory(new PropertyValueFactory<EntryFromTable, String>("column5"));
        column6.setCellValueFactory(new PropertyValueFactory<EntryFromTable, String>("column6"));
        column7.setCellValueFactory(new PropertyValueFactory<EntryFromTable, String>("column7"));
        column8.setCellValueFactory(new PropertyValueFactory<EntryFromTable, String>("column8"));
        column9.setCellValueFactory(new PropertyValueFactory<EntryFromTable, String>("column9"));
        column10.setCellValueFactory(new PropertyValueFactory<EntryFromTable, String>("column10"));

        table.setItems(items);
    }

    private void setColumnsNames(String... columnNames) {
        TableColumn<?, ?>[] columns = {column1, column2, column3, column4, column5, column6, column7, column8, column9, column10};

        for (int i = 0; i < columns.length; i++) {
            if (i < columnNames.length) {
                columns[i].setText(columnNames[i]);
                columns[i].setVisible(true);
            } else {
                columns[i].setVisible(false);
            }
        }
    }


    @FXML
    private void createButtonOnClick(){
        createButton.setDisable(true);
        readButton.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
        customerCreationPane.setVisible(true);
        updatePane.setVisible(false);
    }
    @FXML
    private void readButtonOnClick(){
        createButton.setDisable(false);
        customerCreationPane.setVisible(false);
        updatePane.setVisible(false);
        BusinessEntityManager businessEntityManager = new BusinessEntityManager();

        try{
            ArrayList<BusinessEntity> customers = businessEntityManager.readBusinessEntities();
            ArrayList<EntryFromTable> customersEntry = new ArrayList<>();
            for (BusinessEntity businessEntity : customers) {
                customersEntry.add(businessEntity.toEntryFromTable());
            }
            ObservableList<EntryFromTable> items = FXCollections.observableList(customersEntry);
            setColumnsNames("ID", "First Name", "Last Name", "Is Customer", "Is supplier", "Status", "Id address");
            setTableItems(items);
        }
        catch(SQLException exception){
            Alert sqlAlert = new Alert(AlertType.INFORMATION);
            sqlAlert.setTitle("DataBase error");
            sqlAlert.setContentText("There has been an error in the Data Base");
            sqlAlert.show();
        }

    }
    @FXML
    private void updateButtonOnClick(){
        customerCreationPane.setVisible(false);
        updatePane.setVisible(true);
        createButton.setDisable(false);
        
        updateBusinessEntity = table.getSelectionModel().getSelectedItem();
        if(updateBusinessEntity != null){
            updatePane.setVisible(true);
            updateIsCustomer.setSelected(Boolean.parseBoolean(updateBusinessEntity.getColumn4()));
            updateIsSupplier.setSelected(Boolean.parseBoolean(updateBusinessEntity.getColumn5()));
        }
        else{
            new AdminException(errorNumber.NO_LINE_SELECTED);
        }
    }
    @FXML
    private void deleteButtonOnClick(){
        customerCreationPane.setVisible(false);
        updatePane.setVisible(false);
        createButton.setDisable(false);

        EntryFromTable entryFromTable = table.getSelectionModel().getSelectedItem();
        if(entryFromTable != null){
            BusinessEntityManager businessEntityManager = new BusinessEntityManager();
            try{
                businessEntityManager.deleteBusinessEntity(Integer.parseInt(entryFromTable.getColumn1()));
            }
            catch(SQLException exception){
                Alert sqlAlert = new Alert(AlertType.INFORMATION);
                sqlAlert.setTitle("DataBase error");
                sqlAlert.setContentText("There has been an error in the Data Base");
                sqlAlert.show();
            }
            readButtonOnClick();
        }

        else{
            new AdminException(errorNumber.NO_LINE_SELECTED);
        }
    }

    @FXML
    private void submitButtonOnClick(){
        LocalDate localDate = birthdate.getValue();
        Date sqlDate = null;
        if(localDate != null){
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            java.util.Date utilDate = Date.from(instant);
            sqlDate = new Date(utilDate.getTime());
        }
        AddressManager addressManager = new AddressManager();
        LocalityManager localityManager = new LocalityManager();
        Address newAddress = null;
        if(enterAddress.isSelected()){
            if(!addressIsWrong()) {
                try{
                    Country enteredCountry = new Country(countryChoice.getValue());
                    localityManager.tryCreateLocality(countryChoice.getValue(), postalCode.getText(), locality.getText());
                    Locality newLocality = localityManager.readOnLocality(postalCode.getText(), enteredCountry, locality.getText());
                    addressManager.tryCreateAddress(newLocality, street.getText(), Integer.parseInt(houseNumber.getText()));
                    newAddress = addressManager.readOneAddress(newLocality, street.getText(), Integer.parseInt(houseNumber.getText()));
                }
                catch(SQLException e){
                    
                    Alert sqlAlert = new Alert(AlertType.INFORMATION);
                    sqlAlert.setTitle("DataBase error");
                    sqlAlert.setContentText("There has been an error in the Data Base");
                    sqlAlert.show();
                }
            }

        }
        
        if(!dataAreWrong() && ((newAddress != null && enterAddress.isSelected()) ||  !enterAddress.isSelected())){
            try{
                BusinessEntityManager businessEntityManager = new BusinessEntityManager();
                businessEntityManager.tryCreateBusinessEntity(lastName.getText(), firstName.getText(), sqlDate, isCustomer.isSelected(), isSupplier.isSelected(), newAddress, password.getText(), "dondon");
                readButtonOnClick();
            }
            catch(SQLException exception){
                Alert sqlAlert = new Alert(AlertType.INFORMATION);
                sqlAlert.setTitle("DataBase error");
                sqlAlert.setContentText("There has been an error in the Data Base");
                sqlAlert.show();
            }
        }
    }

    public Boolean dataAreWrong(){
        Integer errorsNb = 0;
        if(lastName.getText().length() == 0){
            new AdminException(errorNumber.LASTNAME_ERROR);
            errorsNb++;
        }
        if(password.getText().length() == 0 || !Objects.equals(password.getText(), confirmPassword.getText())){
            new AdminException(errorNumber.PASSWORD_ERROR);
            errorsNb++;
        }
        return errorsNb > 0;
    }

    public Boolean addressIsWrong(){
        Integer errorsNb = 0;
        if(countryChoice.getValue() == null){
            new AdminException(errorNumber.COUNTRY_ERROR);
            errorsNb++;
        }
        if(postalCode.getText().length() == 0){
            System.out.println("code");
            new AdminException(errorNumber.POSTAL_CODE_ERROR);
            errorsNb++;
        }                
        if(locality.getText().length() == 0){
            System.out.println(postalCode.getText().length());
            new AdminException(errorNumber.LOCALITY_ERROR);
            errorsNb++;
        }
        if(street.getText().length() == 0){
            new AdminException(errorNumber.STREET_ERROR);
            errorsNb++;
        }
        if(houseNumber.getText() == null || houseNumber.getText().length() == 0){
            new AdminException(errorNumber.STREET_ERROR);
            errorsNb++;
        }
        
        System.out.println(errorsNb);
        return errorsNb > 0;
    }

    @FXML
    public void updateSubmitButtonOnClick(){
        BusinessEntityManager businessEntityManager = new BusinessEntityManager();
        try{
            Address addressUpdate = null;
            if(((updateBusinessEntity.getColumn7())) != "null"){
                AddressManager addressManager = new AddressManager();
                addressUpdate = addressManager.readOneAddress(Integer.parseInt((updateBusinessEntity.getColumn7())));
            }
            int serialNumber =  Integer.parseInt(updateBusinessEntity.getColumn1());
            businessEntityManager.updateBusinessEntity(serialNumber, Boolean.parseBoolean(updateBusinessEntity.getColumn4()), 
            Boolean.parseBoolean(updateBusinessEntity.getColumn5()), updateBusinessEntity.getColumn6(), addressUpdate);
            readButtonOnClick();
        }
        catch(SQLException exception){
            Alert sqlAlert = new Alert(AlertType.INFORMATION);
            sqlAlert.setTitle("DataBase error");
            sqlAlert.setContentText("There has been an error in the Data Base");
            sqlAlert.show();
        }

    }

    @FXML 
    public void enterAddressCheck(){

        
        addressPane.setVisible(!addressPane.isVisible());
    }

    @FXML
    void researchButtonOnClick(ActionEvent event)  throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../interfaces/research.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();   
        Scene scene = new Scene(root);  
        stage.setTitle("Research");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void initialize(){            
        try{
            CountryManager countryManager = new CountryManager();
            ArrayList<Country> countries = countryManager.readCountries();
            for (Country country: countries){
                countryChoice.getItems().add(country.getName());
            }
            customerCreationPane.setVisible(false);
            addressPane.setVisible(false);
            updatePane.setVisible(false);
        }
        catch(SQLException exception){
            Alert sqlAlert = new Alert(AlertType.INFORMATION);
            sqlAlert.setTitle("DataBase error");
            sqlAlert.setContentText("There has been an error in the Data Base");
            sqlAlert.show();
        }

    }
}


