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
import java.sql.Date;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import Business.AddressManager;
import Business.BusinessEntityManager;
import Business.CountryManager;
import Business.LocalityManager;
import Controllers.AdminException.errorNumber;




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
    private TableView<TableEntry> table;

    @FXML
    private TableColumn<TableEntry, String> column1;

    @FXML
    private TableColumn<TableEntry, String> column2;

    @FXML
    private TableColumn<TableEntry, String> column3;

    @FXML
    private TableColumn<TableEntry, String> column4;

    @FXML
    private TableColumn<TableEntry, String> column5;

    @FXML
    private TableColumn<TableEntry, String> column6;

    @FXML
    private TableColumn<TableEntry, String> column7;

    @FXML
    private TableColumn<TableEntry, String> column8;

    @FXML
    private TableColumn<TableEntry, String> column9;

    @FXML
    private TableColumn<TableEntry, String> column10;

    private TableEntry updateBusinessEntity;


    private void setTableItems(ObservableList<TableEntry> items) {
        column1.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("column1"));
        column2.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("column2"));
        column3.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("column3"));
        column4.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("column4"));
        column5.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("column5"));
        column6.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("column6"));
        column7.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("column7"));
        column8.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("column8"));
        column9.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("column9"));
        column10.setCellValueFactory(new PropertyValueFactory<TableEntry, String>("column10"));

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
            ArrayList<TableEntry> customersEntry = new ArrayList<>();
            for (BusinessEntity businessEntity : customers) {
                customersEntry.add(businessEntity.toTableEntry());
            }
            ObservableList<TableEntry> items = FXCollections.observableList(customersEntry);
            setColumnsNames("ID", "First Name", "Last Name", "Is Customer", "Is supplier", "Status", "Id address");
            setTableItems(items);
            //tableView.getItems().addAll(customers);
        }
        catch(SQLException exception){
            Alert sqlAlert = new Alert(AlertType.INFORMATION);
            sqlAlert.setTitle("DataBase error");
            sqlAlert.setContentText("There has been an error in the Data Base");
            sqlAlert.show();
        }
        // for (BusinessEntity businessEntity : customers) {
        //     tableView.getItems().add(businessEntity);
        //}
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
    }
    @FXML
    private void deleteButtonOnClick(){
        customerCreationPane.setVisible(false);
        updatePane.setVisible(false);
        createButton.setDisable(false);

        TableEntry tableEntry = table.getSelectionModel().getSelectedItem();
        if(tableEntry != null){
            BusinessEntityManager businessEntityManager = new BusinessEntityManager();
            try{
                businessEntityManager.deleteBusinessEntity(Integer.parseInt(tableEntry.getColumn1()));
            }
            catch(SQLException exception){
                Alert sqlAlert = new Alert(AlertType.INFORMATION);
                sqlAlert.setTitle("DataBase error");
                sqlAlert.setContentText("There has been an error in the Data Base");
                sqlAlert.show();
            }
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
        CountryManager countryManager = new CountryManager();
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
        
        if(!dateAreWrong() && ((newAddress != null && enterAddress.isSelected()) ||  !enterAddress.isSelected())){
            try{
                BusinessEntityManager businessEntityManager = new BusinessEntityManager();
                businessEntityManager.tryCreateBusinessEntity(lastName.getText(), firstName.getText(), sqlDate, isCustomer.isSelected(), isSupplier.isSelected(), newAddress, password.getText(), "dondon");
                if(firstName.getText() == null){
                    //throw exception
                }
            }
            catch(SQLException exception){
                Alert sqlAlert = new Alert(AlertType.INFORMATION);
                sqlAlert.setTitle("DataBase error");
                sqlAlert.setContentText("There has been an error in the Data Base");
                sqlAlert.show();
            }
        }
    }

    public Boolean dateAreWrong(){
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
