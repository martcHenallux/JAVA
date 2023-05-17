package Exception;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AdminException extends Exception{
    
    public static enum errorNumber {LASTNAME_ERROR, EMAIL_ERROR, PASSWORD_ERROR, LOCALITY_ERROR, COUNTRY_ERROR, 
        POSTAL_CODE_ERROR, STREET_ERROR, HOUSE_NUMBER_ERROR};
    private static String[] errorNames= {"Incorrect Last Name", "Incorrect email address", 
        "Password incorrect or doesn't match with each others", "Incorrect Locality", "No country chosen", 
        "Incorrect postal code", "incorrect street", "incorrect house number"};

    public void Display(int errorNumber){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Input error");
        alert.setContentText(errorNames[errorNumber]);
        alert.show();
    }

    public AdminException(errorNumber error){
        super(errorNames[error.ordinal()]);
        Display(error.ordinal());
    }
}
