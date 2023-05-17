package Controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import Business.BusinessEntityManager;
import Business.DocumentManager;
import Business.DocumentTypeManager;
import Business.ProductManager;
import Model.BankInformations;
import Model.BusinessEntity;
import Model.Product;
import Model.Document;
import Model.DocumentType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class ResearchController {

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

    @FXML
    private Button amountToPayButton;

    @FXML
    private Button bestProductSoldForEntityButton;

    @FXML
    private Button getBankInformationsOfEntityFromDocumentButton;

    @FXML
    private Button createButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button readButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button confirmDocument;

    @FXML
    private Label display;

    @FXML
    private Label text;

    @FXML
    private Label nameOne;

    @FXML
    private Label quantityOne;
    
    @FXML
    private Label prixParLitreOne;

    @FXML
    private Label nameTwo;

    @FXML
    private Label quantityTwo;
    
    @FXML
    private Label prixParLitreTwo;

    @FXML 
    private ComboBox<Product> productOneBox;

    @FXML 
    private ComboBox<Product> productTwoBox;

    @FXML
    private Pane displayProductOne;

    @FXML
    private Pane displayProductTwo;


    private int research;
    private ArrayList<Product> products;
    private ArrayList<Integer> idProducts;

    private void setTableValues(ObservableList<EntryFromTable> values) {
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
        table.setItems(values);
    }

    private void setColumnsNames(String... names) {
        TableColumn<?, ?>[] columns = {column1, column2, column3, column4, column5, column6, column7, 
            column8, column9, column10};
            
        for (int i = 0; i < columns.length; i++) {
            if (i < names.length) {
                columns[i].setText(names[i]);
                columns[i].setVisible(true);
            } else {
                columns[i].setVisible(false);
            }
        }
    }

    

    @FXML
    void readButtonOnClick(ActionEvent event) {
        EntryFromTable entryFromTable = table.getSelectionModel().getSelectedItem();
        if(entryFromTable != null){
        switch (research) {
            case 1:
                    try {
                        BusinessEntityManager businessEntityManager = new BusinessEntityManager();
                        BusinessEntity businessEntity = businessEntityManager.readBusinessEntity(Integer.parseInt(entryFromTable.getColumn1()));
                        float result = businessEntityManager.amountToPay(businessEntity);
                        display.setText(Float.toString(result));
                    } catch (SQLException e) {
                        Alert sqlAlert = new Alert(AlertType.INFORMATION);
                        sqlAlert.setTitle("DataBase error");
                        sqlAlert.setContentText("There has been an error in the Data Base");
                        sqlAlert.show();
                    }
                break;
        
            case 2:
                try{
                    ProductManager productManager = new ProductManager();
                    Product product = productManager.bestProductSoldForEntity(Integer.parseInt(entryFromTable.getColumn1()));
                    display.setText(product.getTag());
                } catch (SQLException e) {
                        Alert sqlAlert = new Alert(AlertType.INFORMATION);
                        sqlAlert.setTitle("DataBase error");
                        sqlAlert.setContentText("There has been an error in the Data Base");
                        sqlAlert.show();
                    }
                break;
            
            case 3:
                try{
                    DocumentManager documentManager = new DocumentManager();
                    DocumentTypeManager documentTypeManager = new DocumentTypeManager();
                    DocumentType documentType = documentTypeManager.readOneDocumentType(entryFromTable.getColumn4());
                    Document document = documentManager.readOneDocument(documentType, Integer.parseInt(entryFromTable.getColumn1()));
                    ArrayList<BankInformations> bankInformations = documentManager.getBankAccountsOfEntityFromDocument(document);
                    StringBuilder output = new StringBuilder();
                    output.append("Bic :")
                    .append(bankInformations.get(0).getBicCode())
                    .append(" Iban :")
                    .append(bankInformations.get(0).getIban());
                    text.setText(output.toString());

                } catch (SQLException e) {
                        Alert sqlAlert = new Alert(AlertType.INFORMATION);
                        sqlAlert.setTitle("DataBase error");
                        sqlAlert.setContentText("There has been an error in the Data Base");
                        sqlAlert.show();
                    }
                break;
        }
    }
    }

    @FXML
    public void amountToPay(){      
        readButton.setDisable(false); 
        BusinessEntityManager businessEntityManager = new BusinessEntityManager();

        try{
            ArrayList<BusinessEntity> customers = businessEntityManager.readBusinessEntities();
            ArrayList<EntryFromTable> customersEntry = new ArrayList<>();
            for (BusinessEntity businessEntity : customers) {
                customersEntry.add(businessEntity.toEntryFromTable());
            }
            ObservableList<EntryFromTable> value = FXCollections.observableList(customersEntry);
            setColumnsNames("ID", "First Name", "Last Name", "Is Customer", "Is supplier", "Status", "Id address");
            setTableValues(value);
            research = 1;
            text.setText("Amount to pay:");
        }
        catch(SQLException exception){
            Alert sqlAlert = new Alert(AlertType.INFORMATION);
            sqlAlert.setTitle("DataBase error");
            sqlAlert.setContentText("There has been an error in the Data Base");
            sqlAlert.show();
        }
    }

    @FXML
    public void bestProductSoldForEntity(){
        readButton.setDisable(false);    
        BusinessEntityManager businessEntityManager = new BusinessEntityManager();

        try{
            ArrayList<BusinessEntity> customers = businessEntityManager.readBusinessEntities();
            ArrayList<EntryFromTable> customersEntry = new ArrayList<>();
            for (BusinessEntity businessEntity : customers) {
                customersEntry.add(businessEntity.toEntryFromTable());
            }
            ObservableList<EntryFromTable> values = FXCollections.observableList(customersEntry);
            setColumnsNames("ID", "First Name", "Last Name", "Is Customer", "Is supplier", "Status", "Id address");
            setTableValues(values);
            research = 2;
            text.setText("produit");
        }
        catch(SQLException exception){
            Alert sqlAlert = new Alert(AlertType.INFORMATION);
            sqlAlert.setTitle("DataBase error");
            sqlAlert.setContentText("There has been an error in the Data Base");
            sqlAlert.show();
        }
    }

    @FXML
    public void getBankInformationsOfEntityFromDocument(){  
        readButton.setDisable(false); 
        BusinessEntityManager businessEntityManager = new BusinessEntityManager();

        try{
            ArrayList<BusinessEntity> customers = businessEntityManager.readBusinessEntities();
            ArrayList<EntryFromTable> customersEntry = new ArrayList<>();
            for (BusinessEntity businessEntity : customers) {
                customersEntry.add(businessEntity.toEntryFromTable());
            }
            ObservableList<EntryFromTable> value = FXCollections.observableList(customersEntry);
            setColumnsNames("ID", "priceVATExcluded", "priceVATIncluded", "DocumentType");
            setTableValues(value);
            research = 3;
            text.setText("infos bancaires:");
        }
        catch(SQLException exception){
            Alert sqlAlert = new Alert(AlertType.INFORMATION);
            sqlAlert.setTitle("DataBase error");
            sqlAlert.setContentText("There has been an error in the Data Base");
            sqlAlert.show();
        }

    }


    @FXML
    void compareButton(ActionEvent event) {
        Product productOne = productOneBox.getSelectionModel().getSelectedItem();
        Product productTwo = productTwoBox.getSelectionModel().getSelectedItem();
        if(productOneBox.getSelectionModel().getSelectedItem() != null && productOneBox.getSelectionModel().getSelectedItem() != null){

            displayProductOne.setVisible(false);
            displayProductTwo.setVisible(false); 
            ProductManager productManager = new ProductManager();
            nameOne.setText(productOne.getTag());
            nameTwo.setText(productOne.getTag());
            Object[][] products = productManager.compraringProducts(productOne, productTwo);
            // Float prix = products[0][1];
            // if(products[0][0] == productOne){
            //     prixParLitreOne.setText(Float.toString());
            // }
            nameOne.setText(productOne.getTag());
        }
    }

    @FXML
    public void initialize(){
        createButton.setDisable(true);
        readButton.setDisable(true);
        updateButton.setDisable(true);
        deleteButton.setDisable(true); 
        displayProductOne.setVisible(false);
        displayProductTwo.setVisible(false); 
        ProductManager productManager = new ProductManager();
        try{
            products = productManager.readAllProducts();
            for (Product product: products){
                productOneBox.getItems().add(product);
                productTwoBox.getItems().add(product);
                idProducts.add(product.getProductSerialNumber());
            }
        } catch(SQLException exception){
            Alert sqlAlert = new Alert(AlertType.INFORMATION);
            sqlAlert.setTitle("DataBase error");
            sqlAlert.setContentText("There has been an error in the Data Base");
            sqlAlert.show();
        }

        research = 0;
    }
}
